package net.astrocube.commons.bukkit.party;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.party.PartyMessenger;
import net.astrocube.api.bukkit.party.PartyService;
import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.message.Messenger;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.service.create.CreateService;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.service.update.UpdateService;
import net.astrocube.api.core.virtual.party.Party;
import net.astrocube.api.core.virtual.party.PartyDoc;
import net.astrocube.api.core.virtual.server.ServerDoc;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.bukkit.party.channel.message.PartyInvitationMessage;
import net.astrocube.commons.bukkit.party.channel.message.PartyWarpMessage;
import net.astrocube.commons.bukkit.utils.UserProvideHelper;
import net.astrocube.commons.bukkit.utils.UserUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import redis.clients.jedis.Jedis;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CorePartyService implements PartyService {

	private static final int INVITATION_EXPIRY = 60 * 2;

	private @Inject Plugin plugin;

	private @Inject QueryService<Party> partyQueryService;
	private @Inject FindService<Party> findService;
	private @Inject FindService<User> userFindService;
	private @Inject CreateService<Party, PartyDoc.Partial> partyCreateService;
	private @Inject ObjectMapper objectMapper;
	private @Inject Redis redis;
	private @Inject UserProvideHelper userProvideHelper;

	private @Inject UpdateService<Party, PartyDoc.Partial> updateService;

	private @Inject MessageHandler messageHandler;
	private @Inject PartyMessenger partyMessenger;

	private @Inject Messenger messenger;

	@Override
	public void removeInvite(String playerName) {
		try (Jedis client = redis.getRawConnection().getResource()) {
			client.del("party-invites:" + playerName);
		}
	}

	@Override
	public void cleanupInvitations(Player player) {
		try (Jedis jedis = redis.getRawConnection().getResource()) {
			jedis.keys("party-invitations: " + player.getDatabaseIdentifier() + ":*").forEach(jedis::del);
		}
	}

	@Override
	public Set<String> getInvitations(String playerName) {

		Set<String> invitations = new HashSet<>();
		Optional<User> optional = userProvideHelper.getUserByName(playerName);

		if (optional.isPresent()) {
			User user = optional.get();

			try (Jedis jedis = redis.getRawConnection().getResource()) {
				invitations.addAll(jedis.keys("party-invitations:" + user.getId() + "*"));
			}
		}

		return invitations;
	}

	/*@Override
	public Optional<String> getPartyInviter(String playerName) {
		try (Jedis client = redis.getRawConnection().getResource()) {
			return Optional.ofNullable(client.get("party-invites:" + playerName));
		}
	}*/

	@Override
	public void handleInvitation(Player inviter, Party party, String target) {

		Player playerTarget = Bukkit.getPlayer(target);

		if (playerTarget != null) {
			if (UserUtils.checkSamePlayer(inviter, playerTarget, messageHandler)) {
				return;
			}
		}

		if (!inviter.getDatabaseIdentifier().equals(party.getLeader())) {
			messageHandler.send(inviter, "cannot-invite.not-leader");
			return;
		}

		Optional<User> userOptionalTarget = userProvideHelper.getUserByName(target);

		if (!userOptionalTarget.isPresent()) {
			messageHandler.send(inviter, "");
			return;
		}
		User userInvited = userOptionalTarget.get();

		if (getPartyOf(userInvited.getId()).isPresent()) {
			messageHandler.sendReplacing(
				inviter, "cannot-invite.already-invited",
				"%target%", userInvited.getUsername()
			);
		}

		try (Jedis client = redis.getRawConnection().getResource()) {
			String key = "party-invitations:" + userInvited.getId() + ":" + inviter.getDatabaseIdentifier();
			client.set(key, inviter.getName().toLowerCase());
			client.expire(key, INVITATION_EXPIRY);
		}

		if (playerTarget != null) {
			handleRequestInvitation(inviter.getName(), playerTarget);
			return;
		}

		try {
			messenger.getChannel(PartyInvitationMessage.class).sendMessage(new PartyInvitationMessage(inviter.getName(), userInvited.getUsername()), null);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		if (!party.getMembers().isEmpty()) {
			partyMessenger.sendMessage(party, "party-player-invited-notification", "%leader%", inviter.getName(), "%invited%", userInvited.getUsername());
		}

	}

	@Override
	public void handleRequestInvitation(String inviter, Player invited) {
		invited.sendMessage(
			new ComponentBuilder(messageHandler.replacing(invited, "party-invited", "%inviter%", inviter))
				.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party accept " + inviter))
				.event(new HoverEvent(
						HoverEvent.Action.SHOW_TEXT,
						new ComponentBuilder
							(
								messageHandler.get(invited, "party-invited-hover")
							).create()
					)
				).create());
	}

	@Override
	public void handleAcceptInvitation(Player invited, String inviter) {
		if (getPartyOf(invited.getDatabaseIdentifier()).isPresent()) {
			messageHandler.send(invited, "already-in-party");
			return;
		}

		Set<String> invitations = getInvitations(invited.getName());

		if (invitations.isEmpty() || invitations.contains(inviter)) {
			messageHandler.send(invited, "no-party-invitation");
			return;
		}

		userProvideHelper.getUserByName(inviter).flatMap(userInvited -> getPartyOf(userInvited.getId())).ifPresent(party -> {
			party.getMembers().add(invited.getDatabaseIdentifier());
			messageHandler.send(invited, "joined-party");
			updateService.update(party);

			partyMessenger.sendMessage(party, "join-party", "%player%", invited.getName());
		});

		cleanupInvitations(invited);
	}

	@Override
	public void warp(Player player, Party party) {
		if (!party.getLeader().equals(player.getDatabaseIdentifier())) {
			messageHandler.send(player, "");
			return;
		}
		ServerDoc.Type type = ServerDoc.Type.valueOf(plugin.getConfig().getString("server.type"));

		if (type != ServerDoc.Type.LOBBY) {
			messageHandler.send(player, "");
			return;
		}

		if (party.getMembers().isEmpty()) {
			messageHandler.send(player, "");
			return;
		}

		userFindService.find(player.getDatabaseIdentifier()).callback(response -> {
			if (response != null && response.getResponse().isPresent()) {
				String serverName = response.getResponse().get().getSession().getLastLobby();

				try {
					messenger.getChannel(PartyWarpMessage.class).sendMessage(new PartyWarpMessage(party.getId(), party.getMembers(), serverName), null);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}
		});

	}

	@Override
	public Optional<Party> getParty(String partyId) {

		AtomicReference<Optional<Party>> optional = new AtomicReference<>();
		findService.find(partyId).callback(response -> {
			if (response != null && response.getResponse().isPresent()) {
				optional.set(response.getResponse());
			}
		});

		return optional.get();
	}

	@Override
	public Optional<Party> getPartyOf(String userId) {

		ObjectNode asMemberQuery = objectMapper.createObjectNode();
		// members set also contain the leader
		asMemberQuery.putArray("members").add(userId);
		Set<Party> matches;

		try {
			// we can optimise it using a 'queryOneSync', but QueryService
			// doesn't allow it yet.
			matches = partyQueryService.querySync(asMemberQuery)
				.getFoundModels();
		} catch (Exception e) {
			Logger.getGlobal()
				.log(Level.WARNING, "[Commons] Failed to query party of user " + userId, e);
			return Optional.empty();
		}

		if (matches.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(matches.iterator().next());
		}
	}

	@Override
	public Party createParty(String leaderId) {
		Set<String> members = new HashSet<>();
		members.add(leaderId);
		PartyDoc.Partial party = new PartyDoc.Creation() {
			@Override
			public String getLeader() {
				return leaderId;
			}

			@Override
			public Set<String> getMembers() {
				return members;
			}
		};

		try {
			return partyCreateService.createSync(party);
		} catch (Exception e) {
			throw new IllegalStateException(
				"Error occurred while creating party with leader " + leaderId,
				e
			);
		}
	}

}

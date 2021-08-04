package net.astrocube.commons.bukkit.party;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.party.PartyMessenger;
import net.astrocube.api.bukkit.party.PartyService;
import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.service.create.CreateService;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.party.Party;
import net.astrocube.api.core.virtual.party.PartyDoc;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.bukkit.party.channel.message.PartyInvitationMessage;
import net.astrocube.commons.bukkit.utils.UserProvideHelper;
import net.astrocube.commons.bukkit.utils.UserUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import redis.clients.jedis.Jedis;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CorePartyService implements PartyService {

	private static final int INVITATION_EXPIRY = 60 * 2;

	private @Inject QueryService<Party> partyQueryService;
	private @Inject FindService<Party> findService;
	private @Inject CreateService<Party, PartyDoc.Partial> partyCreateService;
	private @Inject ObjectMapper objectMapper;
	private @Inject Redis redis;
	private @Inject UserProvideHelper userProvideHelper;

	private @Inject MessageHandler messageHandler;
	private @Inject PartyMessenger partyMessenger;
	private @Inject Channel<PartyInvitationMessage> partyInvitationMessageChannel;

	@Override
	public void removeInvite(String playerName) {
		try (Jedis client = redis.getRawConnection().getResource()) {
			client.del("party-invites:" + playerName);
		}
	}

	@Override
	public Optional<String> getPartyInviter(String playerName) {
		try (Jedis client = redis.getRawConnection().getResource()) {
			return Optional.ofNullable(client.get("party-invites:" + playerName));
		}
	}

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

		if (getPartyOf(userInvited.getId()).isPresent() || getPartyInviter(userInvited.getId()).isPresent()) {
			messageHandler.sendReplacing(
				inviter, "cannot-invite.already-invited",
				"%target%", userInvited.getUsername()
			);
		}

		try (Jedis client = redis.getRawConnection().getResource()) {
			String key = "party-invites:" + userInvited.getUsername();
			client.set(key, inviter.getDatabaseIdentifier());
			client.expire(key, INVITATION_EXPIRY);
		}

		if (playerTarget != null) {
			handleRequestInvitation(inviter.getName(), playerTarget);
			return;
		}

		try {
			partyInvitationMessageChannel.sendMessage(new PartyInvitationMessage(inviter.getName(), userInvited.getUsername()), null);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		if (!party.getMembers().isEmpty()) {
			partyMessenger.sendMessage(party, "party.player-invited-notification", "%invited%", userInvited.getUsername());
		}

	}

	@Override
	public void handleRequestInvitation(String inviter, Player invited) {
		invited.sendMessage(
			new ComponentBuilder(messageHandler.replacing("party-invited", "%inviter%", inviter))
				.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/"))
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

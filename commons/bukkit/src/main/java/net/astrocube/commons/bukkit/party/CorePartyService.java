package net.astrocube.commons.bukkit.party;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.party.PartyService;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.service.create.CreateService;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.party.Party;
import net.astrocube.api.core.virtual.party.PartyDoc;
import net.astrocube.commons.bukkit.utils.UserUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import redis.clients.jedis.Jedis;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Common default implementation of {@link PartyService}
 */
public class CorePartyService implements PartyService {

	private static final int INVITATION_EXPIRY = 60 * 2;

	private @Inject QueryService<Party> partyQueryService;
	private @Inject CreateService<Party, PartyDoc.Partial> partyCreateService;
	private @Inject ObjectMapper objectMapper;
	private @Inject Redis redis;

	private @Inject MessageHandler messageHandler;

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

		if(!inviter.getDatabaseIdentifier().equals(party.getLeader())){
			messageHandler.send(inviter, "cannot-invite.not-leader");
			return;
		}

		} else if (
			getPartyOf(invited.getDatabaseIdentifier()).isPresent()
				|| getPartyInviter(invited.getDatabaseIdentifier())
				.isPresent()
		) {
			messageHandler.sendReplacing(
				inviter, "cannot-invite.already-invited",
				"%target%", invited.getName()
			);
			return;
		}

		messageHandler.sendReplacing(
			invited, "party-invited",
			"%inviter%", inviter.getName()
		);

		try (Jedis client = redis.getRawConnection().getResource()) {
			String key = "party-invites:" + invited.getName();
			client.set(key, inviter.getDatabaseIdentifier());
			client.expire(key, INVITATION_EXPIRY);
		}
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

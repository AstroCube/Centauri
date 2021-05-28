package net.astrocube.commons.bukkit.game.match;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.game.match.MatchSubscription;
import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.service.find.FindService;
import redis.clients.jedis.Jedis;

import java.util.Optional;

@Singleton
public class CoreActualMatchCache implements ActualMatchCache {

	private @Inject FindService<Match> matchFindService;
	private @Inject ObjectMapper objectMapper;
	private @Inject Redis redis;

	private void updateSubscription(
		Jedis resource,
		String userId,
		MatchSubscription.Type type,
		String match
	) throws Exception {
		MatchSubscription subscription = new MatchSubscription(match, type);
		resource.set("matchsub:" + userId, objectMapper.writeValueAsString(subscription));
	}

	private void clearSubscription(Jedis resource, String userId) {
		resource.del("matchsub:" + userId);
	}

	@Override
	public void updateSubscription(String id, MatchSubscription subscription) throws Exception {
		try (Jedis resource = redis.getRawConnection().getResource()) {
			updateSubscription(resource, id, subscription.getType(), subscription.getMatch());
		}
	}

	@Override
	public void clearSubscription(String userId) {
		try (Jedis resource = redis.getRawConnection().getResource()) {
			clearSubscription(resource, userId);
		}
	}

	@Override
	public Optional<MatchSubscription> getSubscription(String userId) throws Exception {
		try (Jedis resource = redis.getRawConnection().getResource()) {
			String json = resource.get("matchsub:" + userId);
			if (json == null) {
				return Optional.empty();
			} else {
				return Optional.of(objectMapper.readValue(json, MatchSubscription.class));
			}
		}
	}

	@Override
	public void updateSubscription(Match match, MatchAssignable assignable) throws Exception {
		try (Jedis resource = redis.getRawConnection().getResource()) {
			// update the responsible
			updateSubscription(resource, assignable.getResponsible(), MatchSubscription.Type.ASSIGNATION_RESPONSIBLE, match.getId());
			// update the involved users
			for (String involvedId : assignable.getInvolved()) {
				updateSubscription(resource, involvedId, MatchSubscription.Type.ASSIGNATION_INVOLVED, match.getId());
			}
		}
	}

	@Override
	public Optional<Match> get(String id) throws Exception {
		MatchSubscription subscription = getSubscription(id).orElse(null);
		// no subscription, no match
		if (subscription == null) {
			return Optional.empty();
		} else {
			Match match = matchFindService.findSync(subscription.getMatch());
			if (match == null
				|| match.getStatus() == MatchDoc.Status.FINISHED
				|| match.getStatus() == MatchDoc.Status.INVALIDATED) {
				return Optional.empty();
			} else {
				return Optional.of(match);
			}
		}
	}

	@Override
	public void clearSubscriptions(Match match) {
		try (Jedis resource = redis.getRawConnection().getResource()) {
			for (String spectatorId : match.getSpectators()) clearSubscription(resource, spectatorId);
			for (MatchDoc.Team team : match.getTeams()) {
				for (MatchDoc.TeamMember member : team.getMembers()) {
					if (member.isActive()) {
						clearSubscription(resource, member.getUser());
					}
				}
			}
			for (MatchAssignable assignable : match.getPending()) {
				clearSubscription(resource, assignable.getResponsible());
				for (String involved : assignable.getInvolved()) {
					clearSubscription(resource, involved);
				}
			}
		}

	}
}

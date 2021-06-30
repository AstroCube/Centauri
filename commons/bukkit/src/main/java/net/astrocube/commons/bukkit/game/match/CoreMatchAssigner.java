package net.astrocube.commons.bukkit.game.match;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.event.game.GameUserDisconnectEvent;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.game.match.MatchAssigner;
import net.astrocube.api.bukkit.game.match.MatchService;
import net.astrocube.api.bukkit.game.match.MatchSubscription;
import net.astrocube.api.bukkit.game.match.UserMatchJoiner;
import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.game.matchmaking.SingleMatchAssignation;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.message.Messenger;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.service.update.UpdateService;
import net.astrocube.api.core.virtual.server.Server;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.bukkit.game.match.control.CoreMatchParticipantsProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;
import java.util.logging.Level;

@Singleton
public class CoreMatchAssigner implements MatchAssigner {

	@Inject private Plugin plugin;
	@Inject private ActualMatchCache actualMatchCache;
	@Inject private UserMatchJoiner userMatchJoiner;
	@Inject private FindService<User> userFindService;
	@Inject private FindService<Server> serverFindService;
	@Inject private FindService<Match> matchFindService;
	@Inject private MatchService matchService;
	@Inject private UpdateService<Match, MatchDoc.Partial> updateService;

	private final JedisPool jedisPool;
	private final Channel<SingleMatchAssignation> channel;

	@Inject
	public CoreMatchAssigner(
		Redis redis,
		Messenger messenger
	) {
		this.jedisPool = redis.getRawConnection();
		this.channel = messenger.getChannel(SingleMatchAssignation.class);
	}

	@Override
	public void assign(MatchAssignable assignable, Match match) throws Exception {

		actualMatchCache.updateSubscription(match, assignable);
		Server matchServer = serverFindService.findSync(match.getServer());

		try (Jedis jedis = jedisPool.getResource()) {
			if (!jedis.exists("matchmaking:" + assignable.getResponsible())) {
				throw new GameControlException("No matchmaking request found for this assignation");
			}

			jedis.del("matchmaking:" + assignable.getResponsible());

			System.out.println("Mach pending size " + match.getPending().size());

			match.getPending().add(assignable);
			updateService.updateSync(match);

			ystem.out.println("Mach pending size " + match.getPending().size());

			System.out.println("Updated match, and added a match assignable");
			System.out.println("---- USERS ----");

			CoreMatchParticipantsProvider.getPendingIds(match)
				.forEach(id -> System.out.println("User " + id));

			// TODO: This call is probably not necessary, since we already called updateSubscription(...)
			matchService.assignPending(assignable, match.getId());
			this.setRecord(assignable.getResponsible(), match.getId(), matchServer.getSlug());

			assignable.getInvolved().forEach(involved -> {
				try {
					this.setRecord(involved, match.getId(), matchServer.getSlug());
				} catch (Exception e) {
					plugin.getLogger().log(Level.SEVERE, "There was an error assigning a match user", e);
				}
			});

		}
	}

	@Override
	public void unAssign(Player player) throws Exception {

		Optional<MatchSubscription> optSubscription = actualMatchCache.getSubscription(player.getDatabaseIdentifier());
		if (!optSubscription.isPresent()) {
			return;
		}

		MatchSubscription subscription = optSubscription.get();
		Match match = matchFindService.findSync(subscription.getMatch());
		UserMatchJoiner.Origin origin;

		// TODO: I think we should update the Match too

		switch (subscription.getType()) {
			case SPECTATOR: {
				matchService.assignSpectator(match, player.getDatabaseIdentifier(), false);
				match.getSpectators().remove(player.getDatabaseIdentifier());
				origin = UserMatchJoiner.Origin.SPECTATING;
				break;
			}
			case ASSIGNATION_INVOLVED:
			case ASSIGNATION_RESPONSIBLE: {
				// TODO: Refactor this XD
				match.getPending().removeIf(assignable -> {
					boolean isResponsible = assignable.getResponsible().equals(player.getDatabaseIdentifier());
					boolean isInvolved = assignable.getInvolved().contains(player.getDatabaseIdentifier());
					if (isResponsible || isInvolved) {
						if (isResponsible && assignable.getInvolved().isEmpty()) {
							return true;
						} else if (isResponsible) {
							Iterator<String> involvedIterator = assignable.getInvolved().iterator();
							String newResponsible = involvedIterator.next();
							involvedIterator.remove();
							assignable.setResponsible(newResponsible);
							return false;
						} else {
							assignable.getInvolved().remove(player.getDatabaseIdentifier());
							return false;
						}
					}
					return false;
				});

				matchService.unAssignPending(player.getDatabaseIdentifier(), subscription.getMatch());
				origin = UserMatchJoiner.Origin.WAITING;
				break;
			}
			default: {
				// TODO: REmove the player from the teams, algorithm should be similar to pending
				origin = UserMatchJoiner.Origin.PLAYING;
				break;
			}
		}

		Bukkit.getPluginManager().callEvent(new GameUserDisconnectEvent(subscription.getMatch(), player, origin));
		updateService.updateSync(match);
		actualMatchCache.clearSubscription(player.getDatabaseIdentifier());
	}

	@Override
	public void setRecord(String id, String matchId, String server) throws Exception {
		if (plugin.getConfig().getBoolean("server.sandbox")) {
			User user = userFindService.findSync(id);
			Player player = Bukkit.getPlayer(user.getUsername());
			userMatchJoiner.processJoin(user, player);
		} else {
			channel.sendMessage(new SingleMatchAssignation(id, matchId, server), new HashMap<>());
		}
	}

}

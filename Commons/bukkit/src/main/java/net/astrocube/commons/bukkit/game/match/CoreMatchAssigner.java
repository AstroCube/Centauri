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
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Level;

@Singleton
public class CoreMatchAssigner implements MatchAssigner {

	@Inject private Plugin plugin;
	@Inject private ActualMatchCache actualMatchCache;
	@Inject private UserMatchJoiner userMatchJoiner;
	@Inject private FindService<User> userFindService;
	@Inject private FindService<Server> serverFindService;
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

			match.getPending().add(assignable);
			updateService.updateSync(match);

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
		UserMatchJoiner.Origin origin;

		// TODO: I think we should update the Match too

		switch (subscription.getType()) {
			case SPECTATOR: {
				matchService.assignSpectator(player.getDatabaseIdentifier(), subscription.getMatch(), false);
				origin = UserMatchJoiner.Origin.SPECTATING;
				break;
			}
			case ASSIGNATION_INVOLVED:
			case ASSIGNATION_RESPONSIBLE: {
				matchService.unAssignPending(player.getDatabaseIdentifier(), subscription.getMatch());
				origin = UserMatchJoiner.Origin.WAITING;
				break;
			}
			default: {
				origin = UserMatchJoiner.Origin.PLAYING;
				break;
			}
		}

		Bukkit.getPluginManager().callEvent(new GameUserDisconnectEvent(subscription.getMatch(), player, origin));
		actualMatchCache.clearSubscription(player.getDatabaseIdentifier());
	}

	@Override
	public void setRecord(String id, String matchId, String server) throws Exception {
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.set("matchAssign:" + id, matchId);
			jedis.expire("matchAssign:" + id, 30);
			if (plugin.getConfig().getBoolean("server.sandbox")) {

				User user = userFindService.findSync(id);
				Player player = Bukkit.getPlayer(user.getUsername());

				userMatchJoiner.processJoin(user, player);

			} else {
				channel.sendMessage(new SingleMatchAssignation() {
					@Override
					public String getUser() {
						return id;
					}

					@Override
					public String getMatch() {
						return matchId;
					}

					@Override
					public String getServer() {
						return server;
					}
				}, new HashMap<>());
			}
		}
	}

}

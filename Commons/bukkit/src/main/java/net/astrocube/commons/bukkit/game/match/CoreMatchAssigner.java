package net.astrocube.commons.bukkit.game.match;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.event.game.GameUserDisconnectEvent;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.game.match.MatchAssigner;
import net.astrocube.api.bukkit.game.match.MatchService;
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

		Optional<Match> matchOptional = actualMatchCache.get(player.getDatabaseIdentifier());

		if (matchOptional.isPresent()) {

			Match match = matchOptional.get();

			if (match.getSpectators().contains(player.getDatabaseIdentifier())) {
				matchService.assignSpectator(player.getDatabaseIdentifier(), match.getId(), false);
				Bukkit.getPluginManager().callEvent(new GameUserDisconnectEvent(match.getId(), player, UserMatchJoiner.Origin.SPECTATING));
			} else if (match.getPending().stream().anyMatch(pending ->
				pending.getResponsible().equalsIgnoreCase(player.getDatabaseIdentifier()) ||
					pending.getInvolved().contains(player.getDatabaseIdentifier()))
			) {

				matchService.unAssignPending(player.getDatabaseIdentifier(), match.getId());
				Bukkit.getPluginManager().callEvent(new GameUserDisconnectEvent(match.getId(), player, UserMatchJoiner.Origin.WAITING));

			} else if (match.getTeams().stream()
				.anyMatch(m -> m.getMembers().stream().anyMatch(teamMember ->
					teamMember.getUser().equalsIgnoreCase(player.getDatabaseIdentifier()))
				)
			) {
				Bukkit.getPluginManager().callEvent(new GameUserDisconnectEvent(match.getId(), player, UserMatchJoiner.Origin.PLAYING));
			}

		}


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

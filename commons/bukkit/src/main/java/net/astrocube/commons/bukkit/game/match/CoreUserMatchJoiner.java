package net.astrocube.commons.bukkit.game.match;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.event.game.GameUserJoinEvent;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.game.match.MatchSubscription;
import net.astrocube.api.bukkit.game.match.UserMatchJoiner;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import redis.clients.jedis.JedisPool;

@Singleton
public class CoreUserMatchJoiner implements UserMatchJoiner {

	private final JedisPool jedisPool;
	private final FindService<Match> findService;
	@Inject private ActualMatchCache actualMatchCache;

	@Inject
	public CoreUserMatchJoiner(Redis redis, FindService<Match> findService) {
		this.jedisPool = redis.getRawConnection();
		this.findService = findService;
	}

	@Override
	public void processJoin(User user, Player player) throws Exception {

		MatchSubscription subscription = actualMatchCache.getSubscription(user.getId())
				.orElseThrow(() -> new GameControlException("There was no assignation found for this user"));

		System.out.println("================================");
		System.out.println("  YES BRO UR SUBSCRIBED !!!!!");
		System.out.println("MATCH: " + subscription.getMatch());
		System.out.println("PLAYER: " + player.getName());
		System.out.println("================================");

		Origin origin = UserMatchJoiner.convertSubscriptionToOrigin(subscription.getType());
		Bukkit.getPluginManager().callEvent(new GameUserJoinEvent(subscription.getMatch(), player, origin));
	}

}

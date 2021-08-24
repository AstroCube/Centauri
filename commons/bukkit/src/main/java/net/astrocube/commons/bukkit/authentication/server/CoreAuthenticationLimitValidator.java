package net.astrocube.commons.bukkit.authentication.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.authentication.server.AuthenticationLimitValidator;
import net.astrocube.api.core.redis.Redis;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import redis.clients.jedis.Jedis;

@Singleton
public class CoreAuthenticationLimitValidator implements AuthenticationLimitValidator {

	private static final int MAX_ATTEMPTS = 3;
	private static final int COOL_DOWN = 300;
	private static final long COOL_DOWN_MS = COOL_DOWN * 1000L;

	private @Inject MessageHandler messageHandler;
	private @Inject Plugin plugin;
	private @Inject Redis redis;

	@Override
	public long getRemainingTime(String id) {
		try (Jedis jedis = redis.getRawConnection().getResource()) {
			String passStr = jedis.get("authCooldown:" + id);
			if (passStr == null) {
				return 0L;
			} else {
				return Long.parseLong(passStr) - System.currentTimeMillis();
			}
		}
	}

	@Override
	public void handleFailedAttempt(Player player) {

		String attemptsKey = "authAttempts:" + player.getDatabaseId();
		String cooldownKey = "authCooldown:" + player.getDatabaseId();

		try (Jedis jedis = redis.getRawConnection().getResource()) {

			String triesStr = jedis.get(attemptsKey);
			int attempts = triesStr == null ? 0 : Integer.parseInt(triesStr) + 1;

			if (attempts > MAX_ATTEMPTS) {
				// remove the attempts counter
				jedis.del(attemptsKey);
				// set the player to the cooldown
				jedis.set(cooldownKey, System.currentTimeMillis() + COOL_DOWN_MS + "");
				jedis.expire(cooldownKey, COOL_DOWN);
				// kick the player
				Bukkit.getScheduler().runTask(plugin, () ->
						player.kickPlayer(messageHandler.get(player, "authentication.attempts")));
			} else {
				// update the attempts counter
				jedis.set(attemptsKey, attempts + "");
			}
		}
	}

}

package net.astrocube.commons.bukkit.authentication.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.authentication.server.AuthenticationLimitValidator;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.bukkit.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import redis.clients.jedis.Jedis;

import java.util.Date;


@Singleton
public class CoreAuthenticationLimitValidator implements AuthenticationLimitValidator {

	private @Inject MessageHandler messageHandler;
	private @Inject Plugin plugin;
	private @Inject Redis redis;

	//#region Cooldown
	@Override
	public void setCooldownLock(String id) {
		try (Jedis jedis = redis.getRawConnection().getResource()) {
			jedis.set("authCooldown:" + id, TimeUtils.addMinutes(new Date(), 5).toInstant().toEpochMilli() + "");
			jedis.expire("authCooldown:" + id, 300);
		}
	}

	@Override
	public boolean hasCooldown(String id) {
		try (Jedis jedis = redis.getRawConnection().getResource()) {
			return jedis.exists("authCooldown:" + id);
		}
	}

	@Override
	public Date getRemainingTime(String id) {
		try (Jedis jedis = redis.getRawConnection().getResource()) {
			if (jedis.exists("authCooldown:" + id))
				return new Date(Long.parseLong(jedis.get("authCooldown:" + id)));
			return new Date();
		}
	}
	//#endregion

	@Override
	public void checkAndKick(User user, Player player) {
		if (hasCooldown(user.getId())) {
			Bukkit.getScheduler().runTask(plugin, () -> player.kickPlayer(
				ChatColor.RED + messageHandler.get(player, "authentication.attempts")
			));
		}
	}

	@Override
	public void addTry(User user) {
		try (Jedis jedis = redis.getRawConnection().getResource()) {
			jedis.set("authAttempts:" + user.getId(), getTries(user) + 1 + "");
		}
	}

	@Override
	public void clearTries(User user) {
		try (Jedis jedis = redis.getRawConnection().getResource()) {
			jedis.del("authAttempts:" + user.getId());
		}
	}

	@Override
	public int getTries(User user) {
		try (Jedis jedis = redis.getRawConnection().getResource()) {
			return jedis.exists("authAttempts:" + user.getId()) ?
				Integer.parseInt(jedis.get("authAttempts:" + user.getId())) : 0;
		}
	}

}

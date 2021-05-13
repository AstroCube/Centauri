package net.astrocube.commons.bukkit.authentication.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.authentication.server.AuthenticationCooldown;
import net.astrocube.api.bukkit.authentication.server.CooldownKick;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import redis.clients.jedis.Jedis;


@Singleton
public class CoreCooldownKick implements CooldownKick {

	private @Inject AuthenticationCooldown authenticationCooldown;
	private @Inject MessageHandler messageHandler;
	private @Inject Plugin plugin;
	private @Inject Redis redis;

	@Override
	public void checkAndKick(User user, Player player) {
		if (authenticationCooldown.hasCooldown(user.getId())) {
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

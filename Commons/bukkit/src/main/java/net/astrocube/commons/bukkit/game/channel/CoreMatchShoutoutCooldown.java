package net.astrocube.commons.bukkit.game.channel;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.channel.MatchShoutoutCooldown;
import net.astrocube.api.core.redis.Redis;
import org.bukkit.plugin.Plugin;
import redis.clients.jedis.Jedis;

import java.util.logging.Level;

@Singleton
public class CoreMatchShoutoutCooldown implements MatchShoutoutCooldown {

	private @Inject Redis redis;
	private @Inject Plugin plugin;

	@Override
	public void registerCooldown(String id) {
		try (Jedis jedis = redis.getRawConnection().getResource()) {
			jedis.set("shoutCool:" + id, "true");
		}
	}

	@Override
	public boolean hasCooldown(String id) {

		try (Jedis jedis = redis.getRawConnection().getResource()) {
			return jedis.exists("shoutCool:" + id);
		} catch (Exception e) {
			plugin.getLogger().log(Level.SEVERE, "There was an error obtaining cooldown status", e);
			return true;
		}
	}

}

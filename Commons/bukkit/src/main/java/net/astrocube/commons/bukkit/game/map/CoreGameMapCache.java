package net.astrocube.commons.bukkit.game.map;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.map.GameMapCache;
import net.astrocube.api.core.redis.Redis;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Base64;

@Singleton
public class CoreGameMapCache implements GameMapCache {

	private final JedisPool jedisPool;

	@Inject
	public CoreGameMapCache(Redis redis) {
		this.jedisPool = redis.getRawConnection();
	}

	@Override
	public void registerFile(String id, byte[] file) {
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.set("mapFile:" + id, Base64.getEncoder().encodeToString(file));
			jedis.expire("mapFile:" + id, 21600);
		}
	}

	@Override
	public void registerConfiguration(String id, byte[] file) {
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.set("mapConfig:" + id, Base64.getEncoder().encodeToString(file));
			jedis.expire("mapConfig:" + id, 21600);
		}
	}

	@Override
	public byte[] getFile(String id) throws GameControlException {
		return getNeutral("mapFile:" + id);
	}

	@Override
	public byte[] getConfiguration(String id) throws GameControlException {
		return getNeutral("mapConfig:" + id);
	}

	@Override
	public boolean exists(String id) {
		try (Jedis jedis = jedisPool.getResource()) {
			return jedis.exists("mapConfig:" + id) && jedis.exists("mapFile:" + id);
		}
	}

	private byte[] getNeutral(String match) throws GameControlException {
		try (Jedis jedis = jedisPool.getResource()) {
			if (!jedis.exists(match)) {
				throw new GameControlException("Map is not cached");
			}

			return Base64.getDecoder().decode(jedis.get(match));
		}
	}
}

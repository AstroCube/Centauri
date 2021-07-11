package net.astrocube.commons.bukkit.shutdown;

import net.astrocube.api.core.redis.Redis;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisShutdownHook extends Thread {

	private final JedisPool pool;

	public JedisShutdownHook(Redis redis) {
		this.pool = redis.getRawConnection();
	}

	@Override
	public void run() {
		try (Jedis jedis = pool.getResource()) {
			jedis.flushAll();
		}

		System.out.println("Flushed all");
	}
}
package net.astrocube.commons.bukkit.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.server.ServerDisconnectHandler;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.server.ServerConnectionManager;
import org.bukkit.plugin.Plugin;
import redis.clients.jedis.Jedis;

import java.util.logging.Level;

@Singleton
public class CoreServerDisconnectHandler implements ServerDisconnectHandler {

	private @Inject ServerConnectionManager serverConnectionManager;
	private @Inject Plugin plugin;
	private @Inject Redis redis;

	@Override
	public void execute() {
		try {
			this.serverConnectionManager.endConnection();
		} catch (Exception exception) {
			plugin.getLogger().log(Level.SEVERE, "There was an error while performing server disconnection", exception);
		}

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			try (Jedis jedis = redis.getRawConnection().getResource()) {
				jedis.flushAll();
			}
		}));
	}
}

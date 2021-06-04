package net.astrocube.commons.bungee.listener;

import com.google.inject.Inject;
import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.message.Messenger;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.session.SessionService;
import net.astrocube.api.core.session.SessionSwitchWrapper;
import net.astrocube.api.core.session.registry.SessionRegistryManager;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.bungee.user.UserProvideHelper;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Level;

public class ServerDisconnectListener implements Listener {

	private @Inject UserProvideHelper userProvideHelper;
	private @Inject SessionService sessionService;
	private @Inject SessionRegistryManager sessionRegistryManager;
	private @Inject Plugin plugin;
	private @Inject Redis redis;
	private final Channel<SessionSwitchWrapper> channel;

	@Inject
	public ServerDisconnectListener(Messenger messenger) {
		channel = messenger.getChannel(SessionSwitchWrapper.class);
	}

	@EventHandler
	public void onServerDisconnect(PlayerDisconnectEvent event) {

		try {
			Optional<User> user = userProvideHelper.getUserByName(event.getPlayer().getName());

			if (user.isPresent()) {
				sessionService.serverDisconnect(user.get().getId());
				sessionRegistryManager.unregister(user.get().getId());
				channel.sendMessage(new SessionSwitchWrapper(
						user.get(),
						false
				), new HashMap<>());

				try (Jedis jedis = redis.getRawConnection().getResource()) {
					jedis.del("premium:" + user.get().getUsername());
				} catch (Exception e) {
					throw new Exception("Unable to store premium state");
				}

			}

		} catch (Exception e) {
			plugin.getLogger().log(Level.SEVERE, "Error while obtaining user record", e);
		}

	}

}

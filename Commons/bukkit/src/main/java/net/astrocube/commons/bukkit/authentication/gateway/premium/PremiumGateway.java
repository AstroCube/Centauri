package net.astrocube.commons.bukkit.authentication.gateway.premium;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.authentication.AuthenticationGateway;
import net.astrocube.api.bukkit.authentication.event.AuthenticationSuccessEvent;
import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.message.Messenger;
import net.astrocube.api.core.player.ProxyKickRequest;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import redis.clients.jedis.Jedis;

import java.util.HashMap;

public class PremiumGateway implements AuthenticationGateway {

	private @Inject Redis redis;
	private final Channel<ProxyKickRequest> channel;

	@Inject
	public PremiumGateway(Messenger jedisMessenger) {
		channel = jedisMessenger.getChannel(ProxyKickRequest.class);
	}

	@Override
	public void startProcessing(User user) {

		Player player = Bukkit.getPlayerByIdentifier(user.getId());

		if (player != null) {

			try (Jedis jedis = redis.getRawConnection().getResource()) {

				if (!jedis.exists("premium:" + user.getUsername())) {
					try {
						channel.sendMessage(new ProxyKickRequest(
							player.getName(),
							ChatColor.RED + "Unable to verify premium status, if problem persists contact an administrator"
						), new HashMap<>());
					} catch (Exception ignore) {
					}
					return;
				}

				Bukkit.getPluginManager().callEvent(
					new AuthenticationSuccessEvent(
						this,
						Bukkit.getPlayerByIdentifier(user.getId())
					)
				);

			}

		}

	}

	@Override
	public String getName() {
		return "Premium";
	}


}

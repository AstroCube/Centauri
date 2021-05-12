package net.astrocube.commons.bukkit.listener.authentication;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.authentication.AuthenticationGateway;
import net.astrocube.api.bukkit.authentication.GatewayMatcher;
import net.astrocube.api.bukkit.authentication.event.AuthenticationStartEvent;
import net.astrocube.api.bukkit.authentication.radio.AuthenticationRadio;
import net.astrocube.api.core.authentication.AuthorizeException;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.session.registry.SessionRegistry;
import net.astrocube.api.core.session.registry.SessionRegistryManager;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.bukkit.authentication.AuthenticationUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.time.LocalDateTime;
import java.util.logging.Level;

public class AuthenticationStartListener implements Listener {

	private @Inject SessionRegistryManager sessionRegistryManager;
	private @Inject MessageHandler messageHandler;
	private @Inject GatewayMatcher gatewayMatcher;
	private @Inject AuthenticationRadio authenticationRadio;
	private @Inject FindService<User> findService;
	private @Inject Plugin plugin;

	@EventHandler(priority = EventPriority.LOWEST)
	public void onAuthenticationStart(AuthenticationStartEvent event) {

		this.findService.find(event.getRelated()).callback(userCallback -> {
			try {

				if (!userCallback.isSuccessful() || !userCallback.getResponse().isPresent())
					throw new AuthorizeException("User record not found");

				User user = userCallback.getResponse().get();

				AuthenticationGateway gateway =
					gatewayMatcher.getUserAuthentication(
						user.getSession().getAuthorizeMethod()
					);

				sessionRegistryManager.register(new SessionRegistry() {
					@Override
					public String getUser() {
						return event.getRelated();
					}

					@Override
					public String getVersion() {
						return "1.8.8";
					}

					@Override
					public LocalDateTime getAuthorizationDate() {
						return LocalDateTime.now();
					}

					@Override
					public String getAuthorization() {
						return gateway.getName();
					}

					@Override
					public boolean isPending() {
						return true;
					}

					@Override
					public String getAddress() {
						return event.getPlayer().getAddress().getAddress().getHostAddress();
					}
				});

				if (!event.isRegistered()) {
					gatewayMatcher.getRegisterGateway().startProcessing(user);
				}

				Bukkit.getScheduler().runTask(plugin, () -> {

					Bukkit.getOnlinePlayers().forEach(player -> {
						player.hidePlayer(event.getPlayer());
						event.getPlayer().hidePlayer(player);
					});

					event.getPlayer().teleport(AuthenticationUtils.generateAuthenticationSpawn(plugin.getConfig()));

				});

				if (event.isRegistered()) {
					gateway.startProcessing(user);
				}

				authenticationRadio.addPlayer(event.getPlayer());

				Bukkit.getScheduler().runTaskLater(plugin, () ->
						event.getPlayer().kickPlayer(messageHandler.get(event.getPlayer(), "authentication.time-exceeded")),
					20 * 20);

			} catch (Exception exception) {
				plugin.getLogger().log(Level.SEVERE, "Could not generate authorization for user", exception);
			}

		});
	}

}

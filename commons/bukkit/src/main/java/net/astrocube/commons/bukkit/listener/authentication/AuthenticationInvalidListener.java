package net.astrocube.commons.bukkit.listener.authentication;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.authentication.event.AuthenticationInvalidEvent;
import net.astrocube.api.bukkit.authentication.server.AuthenticationLimitValidator;
import net.astrocube.api.core.authentication.AuthorizeException;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class AuthenticationInvalidListener implements Listener {

	private @Inject AuthenticationLimitValidator authenticationLimitValidator;
	private @Inject MessageHandler messageHandler;
	private @Inject FindService<User> findService;
	private @Inject Plugin plugin;

	@EventHandler(priority = EventPriority.LOWEST)
	public void onInvalidAuthentication(AuthenticationInvalidEvent event) {

		findService.find(event.getPlayer().getDatabaseId()).callback(response -> {
			try {
				if (!response.isSuccessful() || !response.getResponse().isPresent())
					throw new AuthorizeException("Could not find requested user");
				authenticationLimitValidator.handleFailedAttempt(event.getPlayer());
			} catch (AuthorizeException exception) {
				plugin.getLogger().log(Level.WARNING, "Error authorizing player session", exception);
				event.getPlayer().kickPlayer(
					messageHandler.get(event.getPlayer(), "authentication.unauthorized")
						.replace("%error%", exception.getMessage())
				);
			}

		});


	}
}

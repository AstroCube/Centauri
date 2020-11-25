package net.astrocube.commons.bukkit.listener.authentication;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.authentication.event.AuthenticationSuccessEvent;
import net.astrocube.api.core.authentication.AuthorizeException;
import net.astrocube.api.core.session.registry.SessionRegistryManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class AuthenticationSuccessListener implements Listener {

    private @Inject SessionRegistryManager sessionRegistryManager;
    private @Inject MessageHandler<Player> messageHandler;
    private @Inject Plugin plugin;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        try {

            sessionRegistryManager.authorizeSession(
                    event.getPlayer().getDatabaseIdentifier(),
                    event.getGateway().getName()
            );

            event.getPlayer().sendMessage(
                    messageHandler.get(event.getPlayer(), "authentication.success-message")
            );

        } catch (AuthorizeException exception) {
            plugin.getLogger().log(Level.WARNING, "Error authorizing player session", exception);
            event.getPlayer().kickPlayer(
                    messageHandler.get(event.getPlayer(), "authentication.unauthorized")
                            .replace("%%error%%", exception.getMessage())
            );
        }
    }
}

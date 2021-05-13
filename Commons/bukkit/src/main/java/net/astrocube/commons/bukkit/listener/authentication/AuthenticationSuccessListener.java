package net.astrocube.commons.bukkit.listener.authentication;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.authentication.event.AuthenticationSuccessEvent;
import net.astrocube.api.bukkit.authentication.radio.AuthenticationRadio;
import net.astrocube.api.bukkit.teleport.ServerTeleportRetry;
import net.astrocube.api.core.authentication.AuthorizeException;
import net.astrocube.api.core.cloud.CloudStatusProvider;
import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.message.Messenger;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.session.SessionSwitchWrapper;
import net.astrocube.api.core.session.registry.SessionRegistryManager;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.logging.Level;

public class AuthenticationSuccessListener implements Listener {

    private @Inject SessionRegistryManager sessionRegistryManager;
    private @Inject ServerTeleportRetry serverTeleportRetry;
    private @Inject FindService<User> findService;
    private @Inject MessageHandler messageHandler;
    private @Inject CloudStatusProvider cloudStatusProvider;
    private @Inject AuthenticationRadio authenticationRadio;
    private @Inject Plugin plugin;
    private final Channel<SessionSwitchWrapper> channel;

    @Inject
    public AuthenticationSuccessListener(Messenger messenger) {
        channel = messenger.getChannel(SessionSwitchWrapper.class);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        try {

            User user = findService.findSync(event.getPlayer().getDatabaseIdentifier());

            sessionRegistryManager.authorizeSession(
                    event.getPlayer().getDatabaseIdentifier(),
                    event.getGateway().getName()
            );

            event.getPlayer().sendMessage(
                    messageHandler.get(event.getPlayer(), "authentication.success-message")
                    .replace("%player%", event.getPlayer().getName())
            );


            if (cloudStatusProvider.hasCloudHooked()) {

                serverTeleportRetry.attemptGroupTeleport(
                        user.getUsername(),
                        user.getSession().getLastLobby(),
                        1,
                        3
                );
                authenticationRadio.removePlayer(event.getPlayer());
            } else {
                throw new AuthorizeException("Unable to get available register server.");
            }

            channel.sendMessage(new SessionSwitchWrapper() {
                @Override
                public User getUser() {
                    return user;
                }

                @Override
                public boolean isConnecting() {
                    return true;
                }
            }, new HashMap<>());

        } catch (Exception exception) {
            plugin.getLogger().log(Level.WARNING, "Error authorizing player session", exception);
            event.getPlayer().kickPlayer(
                    messageHandler.get(event.getPlayer(), "authentication.unauthorized")
                            .replace("%error%", exception.getMessage())
            );
        }
    }

}

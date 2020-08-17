package net.astrocube.commons.bukkit.listener.authentication;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import net.astrocube.api.bukkit.authentication.AuthenticationGateway;
import net.astrocube.api.bukkit.authentication.GatewayMatcher;
import net.astrocube.api.bukkit.authentication.event.AuthenticationStartEvent;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.session.registry.SessionRegistry;
import net.astrocube.api.core.session.registry.SessionRegistryManager;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.joda.time.DateTime;

import java.util.logging.Level;

public class AuthenticationStartListener implements Listener {

    private @Inject SessionRegistryManager sessionRegistryManager;
    private @Inject GatewayMatcher gatewayMatcher;
    private @Inject FindService<User> findService;
    private @Inject Plugin plugin;

    @EventHandler
    public void onAuthenticationStart(AuthenticationStartEvent event) {
        try {

            User user = this.findService.findSync(event.getRelated());
            AuthenticationGateway gateway =
                    gatewayMatcher.getUserAuthentication(user.getSession().getAuthorizeMethod());

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
                public DateTime getAuthorizationDate() {
                    return new DateTime();
                }

                @Override
                public String getAuthorization() {
                    return gateway.getName();
                }

                @Override
                public boolean isPending() {
                    return true;
                }
            });

            gateway.startProcessing();

        } catch (Exception exception) {
            plugin.getLogger().log(Level.SEVERE, "Could not generate authorization for user", exception);
        }
    }

}

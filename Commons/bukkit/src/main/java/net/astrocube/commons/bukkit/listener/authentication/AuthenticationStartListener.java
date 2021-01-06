package net.astrocube.commons.bukkit.listener.authentication;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import net.astrocube.api.bukkit.authentication.AuthenticationGateway;
import net.astrocube.api.bukkit.authentication.GatewayMatcher;
import net.astrocube.api.bukkit.authentication.event.AuthenticationStartEvent;
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
import org.joda.time.DateTime;

import java.util.logging.Level;

public class AuthenticationStartListener implements Listener {

    private @Inject SessionRegistryManager sessionRegistryManager;
    private @Inject GatewayMatcher gatewayMatcher;
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

                    @Override
                    public String getAddress() {
                        return event.getPlayer().getAddress().getAddress().toString().replace("/", "");
                    }
                });

                if (!event.isRegistered()) {
                    gatewayMatcher.getRegisterGateway().startProcessing(user);
                    return;
                }

                Bukkit.getOnlinePlayers().forEach(player -> {
                    player.hidePlayer(event.getPlayer());
                    event.getPlayer().hidePlayer(player);
                });

                event.getPlayer().teleport(AuthenticationUtils.generateAuthenticationSpawn(plugin.getConfig()));

                gateway.startProcessing(user);

            } catch (Exception exception) {
                plugin.getLogger().log(Level.SEVERE, "Could not generate authorization for user", exception);
            }

        });
    }

}

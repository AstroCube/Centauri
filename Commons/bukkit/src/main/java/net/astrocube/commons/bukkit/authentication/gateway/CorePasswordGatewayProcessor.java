package net.astrocube.commons.bukkit.authentication.gateway;

import com.google.api.client.http.HttpResponseException;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.core.MessageProvider;
import net.astrocube.api.bukkit.authentication.BasicAuthorization;
import net.astrocube.api.bukkit.authentication.GatewayMatcher;
import net.astrocube.api.bukkit.authentication.event.AuthenticationInvalidEvent;
import net.astrocube.api.bukkit.authentication.event.AuthenticationStartEvent;
import net.astrocube.api.bukkit.authentication.event.AuthenticationSuccessEvent;
import net.astrocube.api.bukkit.authentication.gateway.AuthenticationService;
import net.astrocube.api.bukkit.authentication.gateway.PasswordGatewayProcessor;
import net.astrocube.api.bukkit.authentication.server.AuthenticationValidator;
import net.astrocube.api.core.authentication.AuthorizeException;
import net.astrocube.api.core.virtual.user.UserDoc;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

@Singleton
public class CorePasswordGatewayProcessor implements PasswordGatewayProcessor {

    private @Inject AuthenticationService authenticationService;
    private @Inject AuthenticationValidator authenticationValidator;
    private @Inject GatewayMatcher gatewayMatcher;
    private @Inject MessageProvider<Player> messageProvider;
    private @Inject Plugin plugin;

    @Override
    public void validateLogin(Player player, String password) {
        try {

            authenticationValidator.validateAuthenticationAttempt(player);
            authenticationService.login(new BasicAuthorization() {
                @Override
                public String getId() {
                    return player.getDatabaseIdentifier();
                }

                @Override
                public String getPassword() {
                    return password;
                }

                @Override
                public String getAddress() {
                    return player.getAddress().getAddress().toString().replace("/", "");
                }
            });

            Bukkit.getPluginManager().callEvent(new AuthenticationSuccessEvent(
                    gatewayMatcher.getUserAuthentication(UserDoc.Session.Authorization.PASSWORD),
                    player
            ));

        } catch (Exception exception) {

            if (exception instanceof HttpResponseException) {
                HttpResponseException httpResponseException = ((HttpResponseException) exception);

                if (httpResponseException.getStatusCode() == 403) {
                    player.sendMessage(messageProvider.getMessage(player, "authentication.password-invalid"));
                    Bukkit.getPluginManager().callEvent(new AuthenticationInvalidEvent(player));
                    return;
                }
            }

            if (exception instanceof AuthorizeException) {
                Bukkit.getScheduler().runTask(plugin, ()-> player.kickPlayer(
                        messageProvider.getMessage(player, "authentication.unauthorized")
                        .replace("%%error%%", exception.getMessage())
                ));
                return;
            }

            player.sendMessage(messageProvider.getMessage(player, "authentication.password-error"));
            plugin.getLogger().log(Level.WARNING, "Could not perform user login", exception.getMessage());
        }
    }

}

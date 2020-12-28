package net.astrocube.commons.bukkit.authentication.gateway.password;

import com.google.api.client.http.HttpResponseException;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.authentication.GatewayMatcher;
import net.astrocube.api.bukkit.authentication.event.AuthenticationInvalidEvent;
import net.astrocube.api.bukkit.authentication.event.AuthenticationSuccessEvent;
import net.astrocube.api.bukkit.authentication.gateway.AuthenticationService;
import net.astrocube.api.bukkit.authentication.gateway.PasswordGatewayProcessor;
import net.astrocube.api.bukkit.authentication.server.AuthenticationValidator;
import net.astrocube.api.core.virtual.user.UserDoc;
import net.astrocube.commons.bukkit.authentication.gateway.AuthorizationUtils;
import net.astrocube.commons.bukkit.authentication.gateway.register.CoreRegisterGatewayProcessor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@Singleton
public class CorePasswordGatewayProcessor implements PasswordGatewayProcessor {

    private @Inject AuthenticationService authenticationService;
    private @Inject AuthenticationValidator authenticationValidator;
    private @Inject GatewayMatcher gatewayMatcher;
    private @Inject MessageHandler<Player> messageHandler;
    private @Inject Plugin plugin;

    @Override
    public void validateLogin(Player player, String password) {
        try {

            authenticationValidator.validateAuthenticationAttempt(player);
            authenticationService.login(AuthorizationUtils.build(player, password));

            Bukkit.getPluginManager().callEvent(new AuthenticationSuccessEvent(
                    gatewayMatcher.getUserAuthentication(UserDoc.Session.Authorization.PASSWORD),
                    player
            ));

        } catch (Exception exception) {

            if (exception instanceof HttpResponseException) {
                HttpResponseException httpResponseException = ((HttpResponseException) exception);

                if (httpResponseException.getStatusCode() == 403) {
                    messageHandler.send(player, "authentication.password-invalid");
                    Bukkit.getPluginManager().callEvent(new AuthenticationInvalidEvent(player));
                    return;
                }
            }

            AuthorizationUtils.checkError(player, exception, plugin, messageHandler);
        }
    }

}

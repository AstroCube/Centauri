package net.astrocube.commons.bukkit.authentication.gateway;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.core.MessageProvider;
import net.astrocube.api.bukkit.authentication.BasicAuthorization;
import net.astrocube.api.bukkit.authentication.gateway.AuthenticationService;
import net.astrocube.api.bukkit.authentication.gateway.PasswordGatewayProcessor;
import org.apache.http.client.HttpResponseException;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

@Singleton
public class CorePasswordGatewayProcessor implements PasswordGatewayProcessor {

    private @Inject AuthenticationService authenticationService;
    private @Inject MessageProvider<Player> messageProvider;
    private @Inject Plugin plugin;

    @Override
    public void validateLogin(Player player, String password) {
        try {
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
        } catch (Exception exception) {

            if (exception instanceof HttpResponseException) {
                HttpResponseException httpResponseException = ((HttpResponseException) exception);

                if (httpResponseException.getStatusCode() == 403) {
                    player.sendMessage(messageProvider.getMessage(player, "password-invalid"));
                    return;
                }
            }

            player.sendMessage(messageProvider.getMessage(player, "password-error"));
            plugin.getLogger().log(Level.WARNING, "Could not perform user login", exception.getMessage());
        }
    }

}

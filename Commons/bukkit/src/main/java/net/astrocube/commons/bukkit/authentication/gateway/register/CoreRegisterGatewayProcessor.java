package net.astrocube.commons.bukkit.authentication.gateway.register;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.authentication.GatewayMatcher;
import net.astrocube.api.bukkit.authentication.event.AuthenticationSuccessEvent;
import net.astrocube.api.bukkit.authentication.gateway.AuthenticationService;
import net.astrocube.api.bukkit.authentication.gateway.RegisterGatewayProcessor;
import net.astrocube.api.bukkit.authentication.server.AuthenticationValidator;
import net.astrocube.commons.bukkit.authentication.gateway.AuthorizationUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@Singleton
public class CoreRegisterGatewayProcessor implements RegisterGatewayProcessor {

    private @Inject AuthenticationService authenticationService;
    private @Inject AuthenticationValidator authenticationValidator;
    private @Inject GatewayMatcher gatewayMatcher;
    private @Inject MessageHandler messageHandler;
    private @Inject Plugin plugin;

    @Override
    public void validateRegister(Player player, String password) {

        try {
            authenticationValidator.validateAuthenticationAttempt(player);
            authenticationService.register(AuthorizationUtils.build(player, password));

            Bukkit.getPluginManager().callEvent(new AuthenticationSuccessEvent(
                    gatewayMatcher.getRegisterGateway(),
                    player
            ));
        } catch (Exception exception) {
            AuthorizationUtils.checkError(player, exception, plugin, messageHandler);
        }

    }

}

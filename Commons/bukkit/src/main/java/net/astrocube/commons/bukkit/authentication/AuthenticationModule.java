package net.astrocube.commons.bukkit.authentication;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.authentication.GatewayMatcher;
import net.astrocube.api.bukkit.authentication.gateway.AuthenticationService;
import net.astrocube.api.bukkit.authentication.gateway.PasswordGatewayProcessor;
import net.astrocube.api.bukkit.authentication.gateway.RegisterGatewayProcessor;
import net.astrocube.api.bukkit.authentication.server.AuthenticationCooldown;
import net.astrocube.api.bukkit.authentication.server.AuthenticationValidator;
import net.astrocube.api.bukkit.authentication.server.CooldownKick;
import net.astrocube.commons.bukkit.authentication.gateway.password.CorePasswordGatewayProcessor;
import net.astrocube.commons.bukkit.authentication.gateway.register.CoreRegisterGatewayProcessor;
import net.astrocube.commons.bukkit.authentication.radio.AuthenticationRadioModule;
import net.astrocube.commons.bukkit.authentication.server.CoreAuthenticationCooldown;
import net.astrocube.commons.bukkit.authentication.server.CoreAuthenticationValidator;
import net.astrocube.commons.bukkit.authentication.server.CoreCooldownKick;

public class AuthenticationModule extends ProtectedModule {

    @Override
    public void configure() {

        install(new AuthenticationRadioModule());

        bind(AuthenticationCooldown.class).to(CoreAuthenticationCooldown.class);
        bind(CooldownKick.class).to(CoreCooldownKick.class);
        bind(GatewayMatcher.class).to(CoreGatewayMatcher.class);
        bind(AuthenticationValidator.class).to(CoreAuthenticationValidator.class);
        bind(RegisterGatewayProcessor.class).to(CoreRegisterGatewayProcessor.class);
        bind(AuthenticationService.class).to(CoreAuthenticationService.class);
        bind(PasswordGatewayProcessor.class).to(CorePasswordGatewayProcessor.class);
    }

}
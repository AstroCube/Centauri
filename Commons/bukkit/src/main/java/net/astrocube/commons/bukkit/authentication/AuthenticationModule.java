package net.astrocube.commons.bukkit.authentication;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.authentication.GatewayMatcher;
import net.astrocube.api.bukkit.authentication.server.AuthenticationCooldown;
import net.astrocube.api.bukkit.authentication.server.CooldownKick;
import net.astrocube.commons.bukkit.authentication.server.CoreAuthenticationCooldown;
import net.astrocube.commons.bukkit.authentication.server.CoreCooldownKick;

public class AuthenticationModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(AuthenticationCooldown.class).to(CoreAuthenticationCooldown.class);
        bind(CooldownKick.class).to(CoreCooldownKick.class);
        bind(GatewayMatcher.class).to(CoreGatewayMatcher.class);
    }

}
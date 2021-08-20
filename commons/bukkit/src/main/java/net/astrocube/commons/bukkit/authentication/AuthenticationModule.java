package net.astrocube.commons.bukkit.authentication;

import net.astrocube.inject.ProtectedModule;
import net.astrocube.api.bukkit.authentication.GatewayMatcher;
import net.astrocube.api.bukkit.authentication.gateway.AuthenticationService;
import net.astrocube.api.bukkit.authentication.gateway.PasswordGatewayProcessor;
import net.astrocube.api.bukkit.authentication.gateway.RegisterGatewayProcessor;
import net.astrocube.api.bukkit.authentication.server.AuthenticationValidator;
import net.astrocube.api.bukkit.authentication.server.AuthenticationLimitValidator;
import net.astrocube.commons.bukkit.authentication.gateway.password.CorePasswordGatewayProcessor;
import net.astrocube.commons.bukkit.authentication.gateway.register.CoreRegisterGatewayProcessor;
import net.astrocube.commons.bukkit.authentication.radio.AuthenticationRadioModule;
import net.astrocube.commons.bukkit.authentication.server.CoreAuthenticationValidator;
import net.astrocube.commons.bukkit.authentication.server.CoreAuthenticationLimitValidator;

public class AuthenticationModule extends ProtectedModule {

	@Override
	public void configure() {

		install(new AuthenticationRadioModule());

		bind(AuthenticationLimitValidator.class).to(CoreAuthenticationLimitValidator.class);
		bind(GatewayMatcher.class).to(CoreGatewayMatcher.class);
		bind(AuthenticationValidator.class).to(CoreAuthenticationValidator.class);
		bind(RegisterGatewayProcessor.class).to(CoreRegisterGatewayProcessor.class);
		bind(AuthenticationService.class).to(HttpAuthenticationService.class);
		bind(PasswordGatewayProcessor.class).to(CorePasswordGatewayProcessor.class);
	}

}
package net.astrocube.commons.bukkit.authentication;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.authentication.AuthenticationGateway;
import net.astrocube.api.bukkit.authentication.GatewayMatcher;
import net.astrocube.api.core.virtual.user.UserDoc;
import net.astrocube.commons.bukkit.authentication.gateway.password.PasswordGateway;
import net.astrocube.commons.bukkit.authentication.gateway.premium.PremiumGateway;
import net.astrocube.commons.bukkit.authentication.gateway.register.RegisterGateway;

import java.util.HashMap;
import java.util.Map;

public class CoreGatewayMatcher implements GatewayMatcher {

	private final Map<UserDoc.Session.Authorization, AuthenticationGateway> gateways = new HashMap<>();

	private final AuthenticationGateway registerGateway;
	private final AuthenticationGateway passwordGateway;

	@Inject
	public CoreGatewayMatcher(
			RegisterGateway registerGateway,
			PasswordGateway passwordGateway,
			PremiumGateway premiumGateway
	) {
		this.registerGateway = registerGateway;
		this.passwordGateway = passwordGateway;
		gateways.put(UserDoc.Session.Authorization.PASSWORD, passwordGateway);
		gateways.put(UserDoc.Session.Authorization.PREMIUM, premiumGateway);
	}

	@Override
	public AuthenticationGateway getUserAuthentication(UserDoc.Session.Authorization authorizationType) {
		return gateways.getOrDefault(authorizationType, passwordGateway);
	}

	@Override
	public AuthenticationGateway getRegisterGateway() {
		return registerGateway;
	}
}

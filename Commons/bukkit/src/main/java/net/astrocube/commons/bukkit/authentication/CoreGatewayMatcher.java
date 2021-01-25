package net.astrocube.commons.bukkit.authentication;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.authentication.AuthenticationGateway;
import net.astrocube.api.bukkit.authentication.GatewayMatcher;
import net.astrocube.api.core.virtual.user.UserDoc;
import net.astrocube.commons.bukkit.authentication.gateway.password.PasswordGateway;
import net.astrocube.commons.bukkit.authentication.gateway.register.RegisterGateway;

import java.util.HashMap;
import java.util.Map;

public class CoreGatewayMatcher implements GatewayMatcher {

    private final Map<UserDoc.Session.Authorization, AuthenticationGateway> types;
    private final AuthenticationGateway registerGateway;

    @Inject public CoreGatewayMatcher(
            PasswordGateway passwordGateway,
            RegisterGateway registerGateway
    ) {
        this.registerGateway = registerGateway;
        this.types = new HashMap<>();
        types.put(UserDoc.Session.Authorization.PASSWORD, passwordGateway);
    }

    @Override
    public AuthenticationGateway getUserAuthentication(UserDoc.Session.Authorization authorizationType) {
        return types.containsKey(authorizationType) ?
                types.get(authorizationType) :
                types.get(UserDoc.Session.Authorization.PASSWORD);
    }

    @Override
    public AuthenticationGateway getRegisterGateway() {
        return registerGateway;
    }
}

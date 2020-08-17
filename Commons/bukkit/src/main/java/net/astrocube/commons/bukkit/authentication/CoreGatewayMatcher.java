package net.astrocube.commons.bukkit.authentication;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.authentication.GatewayMatcher;
import net.astrocube.api.bukkit.authentication.AuthenticationGateway;
import net.astrocube.api.core.virtual.user.UserDoc;

import java.util.HashMap;
import java.util.Map;

public class CoreGatewayMatcher implements GatewayMatcher {

    private Map<UserDoc.Session.Authorization, AuthenticationGateway> types;

    @Inject public CoreGatewayMatcher() {
        this.types = new HashMap<>();
    }

    @Override
    public AuthenticationGateway getUserAuthentication(UserDoc.Session.Authorization authorizationType) {
        return types.containsKey(authorizationType) ?
                types.get(authorizationType) :
                types.get(UserDoc.Session.Authorization.PASSWORD);
    }
}

package net.astrocube.api.bukkit.authentication;

import net.astrocube.api.core.virtual.user.UserDoc;

public interface GatewayMatcher {

    /**
     * Prepare authentication based on certain enum
     * @param authorizationType of user
     * @return authentication method to be used
     */
    AuthenticationGateway getUserAuthentication(UserDoc.Session.Authorization authorizationType);

    /**
     * Return default gateway performed at register
     * @return authentication method to used when registered
     */
    AuthenticationGateway getRegisterGateway();

}

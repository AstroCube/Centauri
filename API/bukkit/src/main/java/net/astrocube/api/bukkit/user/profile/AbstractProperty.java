package net.astrocube.api.bukkit.user.profile;

import java.security.PublicKey;

/**
 * Mojang Authlib property
 */
public interface AbstractProperty {

    /**
     * @return name of the property
     */
    String getName();

    /**
     * @return value of the property
     */
    String getValue();

    /**
     * @return signature of the property
     */
    String getSignature();

    /**
     * @return if property has signature
     */
    boolean hasSignature();

    /**
     * @param key that will be checked
     * @return if signature is valid
     */
    boolean isSignatureValid(PublicKey key);

}

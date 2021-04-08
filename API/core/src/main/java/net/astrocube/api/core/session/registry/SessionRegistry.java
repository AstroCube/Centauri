package net.astrocube.api.core.session.registry;

import net.astrocube.api.core.model.Document;

import java.time.LocalDateTime;

public interface SessionRegistry extends Document {

    /**
     * User who authorized before the game session
     * @return user linked to session
     */
    String getUser();

    /**
     * Version that user logged in
     * @return minecraft version
     */
    String getVersion();

    /**
     * Date of authorization
     * @return dateTime
     */
    LocalDateTime getAuthorizationDate();

    /**
     * Return identifier of authorization method used by the {@link net.astrocube.api.core.virtual.user.User}
     * @return authorization indicator
     */
    String getAuthorization();

    /**
     * Marked as pending only when the user has not been allowed to access the game
     * @return pending indicator
     */
    boolean isPending();

    /**
     * Address which was registered at authentication to generate post-validations.
     * @return IP address
     */
    String getAddress();

}

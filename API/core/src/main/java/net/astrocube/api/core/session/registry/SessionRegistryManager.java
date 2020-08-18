package net.astrocube.api.core.session.registry;

import net.astrocube.api.core.authentication.AuthorizeException;

import java.util.Optional;

public interface SessionRegistryManager {

    /**
     * Register at Redis a certain {@link SessionRegistry}
     * @param registry to be created
     */
    void register(SessionRegistry registry) throws AuthorizeException;

    /**
     * Obtain certain session registry if user is connected
     * @param id to inspect
     * @return user registry
     */
    Optional<SessionRegistry> getRegistry(String id) throws AuthorizeException;

    /**
     * Unregister a {@link net.astrocube.api.core.virtual.user.User} session
     * @param id of the session to be removed
     */
    void unregister(String id) throws AuthorizeException;

}

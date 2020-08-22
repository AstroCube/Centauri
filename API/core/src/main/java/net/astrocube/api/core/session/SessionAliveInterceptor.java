package net.astrocube.api.core.session;

import net.astrocube.api.core.session.registry.SessionRegistry;

import java.util.Optional;

public interface SessionAliveInterceptor {

    /**
     * Check if user session is alive from local cache and cloud.
     * @param id to be intercepted
     * @return boolean indicating session status
     */
    Optional<SessionRegistry> isAlive(String id);
}

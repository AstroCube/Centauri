package net.astrocube.api.core.session;

import net.astrocube.api.core.session.registry.SessionRegistry;
import net.astrocube.api.core.virtual.user.User;

import java.util.Optional;

public interface SessionAliveInterceptor {

    /**
     * Check if user session is alive from local cache and cloud.
     * @param user to be intercepted
     * @return boolean indicating session status
     */
    Optional<SessionRegistry> isAlive(User user);
}

package net.astrocube.commons.core.session;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.core.authentication.AuthorizeException;
import net.astrocube.api.core.session.SessionAliveInterceptor;
import net.astrocube.api.core.session.registry.SessionRegistry;
import net.astrocube.api.core.session.registry.SessionRegistryManager;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class CoreSessionAliveInterceptor implements SessionAliveInterceptor {

    private @Inject SessionRegistryManager sessionRegistryManager;

    @Override
    public Optional<SessionRegistry> isAlive(String id) {
        try {

            // TODO: Check if user is connected at cloud system and session is alive

            return sessionRegistryManager.getRegistry(id);
        } catch (AuthorizeException exception) {
            Logger.getLogger("session").log(Level.WARNING, "Could not obtain session registry.");
        }

        return Optional.empty();
    }

}

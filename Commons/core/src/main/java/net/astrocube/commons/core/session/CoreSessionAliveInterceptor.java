package net.astrocube.commons.core.session;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.core.authentication.AuthorizeException;
import net.astrocube.api.core.cloud.CloudStatusProvider;
import net.astrocube.api.core.session.SessionAliveInterceptor;
import net.astrocube.api.core.session.registry.SessionRegistry;
import net.astrocube.api.core.session.registry.SessionRegistryManager;
import net.astrocube.api.core.virtual.user.User;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class CoreSessionAliveInterceptor implements SessionAliveInterceptor {

	private @Inject SessionRegistryManager sessionRegistryManager;
	private @Inject CloudStatusProvider cloudStatusProvider;

	@Override
	public Optional<SessionRegistry> isAlive(User user) {
		try {

			if (!cloudStatusProvider.hasAliveSession(user.getUsername())) {
				return Optional.empty();
			}

			return sessionRegistryManager.getRegistry(user.getId());
		} catch (AuthorizeException exception) {
			Logger.getLogger("session").log(Level.WARNING, "Could not obtain session registry.");
		}

		return Optional.empty();
	}

}

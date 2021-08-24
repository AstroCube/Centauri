package net.astrocube.commons.bukkit.authentication.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.authentication.server.AuthenticationValidator;
import net.astrocube.api.core.authentication.AuthorizeException;
import net.astrocube.api.core.session.registry.SessionRegistry;
import net.astrocube.api.core.session.registry.SessionRegistryManager;
import org.bukkit.entity.Player;

import java.util.Optional;

@Singleton
public class CoreAuthenticationValidator implements AuthenticationValidator {

	private @Inject SessionRegistryManager sessionRegistryManager;

	@Override
	public void validateAuthenticationAttempt(Player player) throws AuthorizeException {

		Optional<SessionRegistry> registryOptional =
			sessionRegistryManager.getRegistry(player.getDatabaseId());

		if (!registryOptional.isPresent())
			throw new AuthorizeException("Authorization pre-fetching never performed.");

		SessionRegistry registry = registryOptional.get();

		if (!registry.isPending()) throw new AuthorizeException("Session already authorized");
		if (
			!player.getAddress().getAddress().toString()
				.replace("/", "").equalsIgnoreCase(registry.getAddress())
		) throw new AuthorizeException("IP does not match with pre-authorized");

	}

}

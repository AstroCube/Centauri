package net.astrocube.commons.core.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.core.authentication.AuthorizeException;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.session.registry.SessionRegistry;
import net.astrocube.api.core.session.registry.SessionRegistryManager;
import redis.clients.jedis.Jedis;

import java.time.LocalDateTime;
import java.util.Optional;

@Singleton
public class CoreSessionRegistryManager implements SessionRegistryManager {

	private @Inject Redis redis;
	private @Inject ObjectMapper objectMapper;

	@Override
	public void register(SessionRegistry registry) throws AuthorizeException {
		try (Jedis jedis = redis.getRawConnection().getResource()) {
			jedis.set("session:" + registry.getUser(), objectMapper.writeValueAsString(registry));
		} catch (Exception exception) {
			throw new AuthorizeException("Unable to generate authorization");
		}
	}

	@Override
	public Optional<SessionRegistry> getRegistry(String id) throws AuthorizeException {
		try (Jedis jedis = redis.getRawConnection().getResource()) {
			if (!jedis.exists("session:" + id)) return Optional.empty();
			return Optional.of(objectMapper.readValue(
				jedis.get("session:" + id),
				SessionRegistry.class
			));
		} catch (Exception exception) {
			throw new AuthorizeException("Unable to remove authorization");
		}
	}

	@Override
	public void authorizeSession(String id, String authorizationMethod) throws AuthorizeException {
		try (Jedis jedis = redis.getRawConnection().getResource()) {

			Optional<SessionRegistry> sessionRegistry = getRegistry(id);

			if (!sessionRegistry.isPresent()) throw new AuthorizeException("Session authorization never pre-fetched");

			SessionRegistry registry = sessionRegistry.get();
			registry.setAuthorizationDate(LocalDateTime.now());
			registry.setAuthorization(authorizationMethod);
			registry.setPending(false);

			jedis.set("session:" + id, objectMapper.writeValueAsString(registry));

		} catch (Exception exception) {
			throw new AuthorizeException("Unable to authorize session");
		}
	}

	@Override
	public void unregister(String id) throws AuthorizeException {
		try (Jedis jedis = redis.getRawConnection().getResource()) {
			jedis.del("session:" + id);
		} catch (Exception exception) {
			throw new AuthorizeException("Unable to remove authorization");
		}
	}

}

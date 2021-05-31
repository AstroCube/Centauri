package net.astrocube.commons.bukkit.session.validation;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.session.InvalidSessionMessageMatcher;
import net.astrocube.api.bukkit.session.SessionCacheInvalidator;
import net.astrocube.api.bukkit.session.SessionValidatorHandler;
import net.astrocube.api.core.virtual.session.SessionValidateDoc;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Singleton
public class CoreSessionValidator implements SessionValidatorHandler {

	private final InvalidSessionMessageMatcher invalidSessionMessageMatcher;
	private final SessionCacheInvalidator sessionCacheInvalidator;
	private final Cache<UUID, SessionValidateDoc.Complete> usersBeingValidated;

	@Inject
	CoreSessionValidator(
		InvalidSessionMessageMatcher invalidSessionMessageMatcher,
		SessionCacheInvalidator sessionCacheInvalidator
	) {
		this.invalidSessionMessageMatcher = invalidSessionMessageMatcher;
		this.sessionCacheInvalidator = sessionCacheInvalidator;
		this.usersBeingValidated = CacheBuilder
			.newBuilder()
			.expireAfterWrite(1, TimeUnit.MINUTES)
			.maximumSize(50)
			.weakValues()
			.build();
	}

	@Override
	public void validateSession(AsyncPlayerPreLoginEvent event, SessionValidateDoc.Complete authorization) throws Exception {
		if (authorization.isMultiAccount()) {
			event.setKickMessage(invalidSessionMessageMatcher.generateSessionMessage(authorization.getUser()));
			event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
			return;
		}

		sessionCacheInvalidator.invalidateSessionCache(authorization.getUser());
		usersBeingValidated.put(event.getUniqueId(), authorization);
	}

	@Nullable
	@Override
	public SessionValidateDoc.Complete getValidationPendingUser(UUID uuid) {
		SessionValidateDoc.Complete validator = usersBeingValidated.getIfPresent(uuid);
		usersBeingValidated.invalidate(uuid);
		return validator;
	}

}

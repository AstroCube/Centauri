package net.astrocube.api.bukkit.session;

import net.astrocube.api.core.virtual.session.SessionValidateDoc;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import javax.annotation.Nullable;
import java.util.UUID;

public interface SessionValidatorHandler {

	/**
	 * Compare and allow/disallow event connection depending on backend response
	 * @param event         to be compared
	 * @param authorization to be provided
	 */
	void validateSession(AsyncPlayerPreLoginEvent event, SessionValidateDoc.Complete authorization) throws Exception;

	/**
	 * Will return specific cached user to complete the validation requirements
	 * @param uuid of the login event player
	 * @return user to be handled
	 */
	@Nullable
	SessionValidateDoc.Complete getValidationPendingUser(UUID uuid);

}

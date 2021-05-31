package net.astrocube.api.bukkit.authentication.server;

import net.astrocube.api.core.authentication.AuthorizeException;
import org.bukkit.entity.Player;

public interface AuthenticationValidator {

	/**
	 * Validate if authentication of player is correctly registered at pre-login issuing.
	 * @param player to be validated
	 */
	void validateAuthenticationAttempt(Player player) throws AuthorizeException;
}

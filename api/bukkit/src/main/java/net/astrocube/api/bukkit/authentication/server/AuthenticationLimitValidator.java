package net.astrocube.api.bukkit.authentication.server;

import org.bukkit.entity.Player;

public interface AuthenticationLimitValidator {

	/**
	 * Obtain the remaining time in milliseconds
	 * for the next login attempt
	 */
	long getRemainingTime(String id);

	/**
	 * Accumulates a failed attempt to cache and
	 * checks if the player exceeded the max
	 * attempts, if it does, the player gets
	 * kicked and must wait "X" seconds for its next
	 * login.
	 */
	void handleFailedAttempt(Player player);

}

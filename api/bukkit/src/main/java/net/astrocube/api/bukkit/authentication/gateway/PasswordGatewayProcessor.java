package net.astrocube.api.bukkit.authentication.gateway;

import org.bukkit.entity.Player;

public interface PasswordGatewayProcessor {

	/**
	 * Performs corresponding steps to communicate login with
	 * backend.
	 * @param player   to be validated
	 * @param password to be matched
	 */
	void validateLogin(Player player, String password);

}

package net.astrocube.api.bukkit.authentication.gateway;

import org.bukkit.entity.Player;

public interface RegisterGatewayProcessor {

	/**
	 * Validate if register data is correct
	 * @param player   to be registered
	 * @param password to be stored
	 */
	void validateRegister(Player player, String password);

}

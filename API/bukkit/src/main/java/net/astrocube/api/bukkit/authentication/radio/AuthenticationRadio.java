package net.astrocube.api.bukkit.authentication.radio;

import org.bukkit.entity.Player;

public interface AuthenticationRadio {

	/**
	 * Add player to the authentication radio
	 * @param player to be added
	 */
	void addPlayer(Player player);

	/**
	 * Remove player from authentication radio
	 * @param player to be removed
	 */
	void removePlayer(Player player);

}

package net.astrocube.api.bukkit.lobby.selector.lobby;

import org.bukkit.entity.Player;

public interface LobbySelectorDisplay {

	/**
	 * Open lobby selector display for a certain player
	 * @param player to be displayed
	 * @param page   to be displayed
	 */
	void openDisplay(Player player, int page);

}

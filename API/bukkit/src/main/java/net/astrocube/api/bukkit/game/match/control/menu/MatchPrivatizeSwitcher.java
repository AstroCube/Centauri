package net.astrocube.api.bukkit.game.match.control.menu;

import org.bukkit.entity.Player;

public interface MatchPrivatizeSwitcher {

	/**
	 * Switch match privatization
	 * @param player who privatized
	 */
	void switchPrivatization(Player player) throws Exception;

}

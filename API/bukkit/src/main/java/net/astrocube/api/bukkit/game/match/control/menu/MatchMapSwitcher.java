package net.astrocube.api.bukkit.game.match.control.menu;

import net.astrocube.api.bukkit.virtual.game.match.Match;
import org.bukkit.entity.Player;

public interface MatchMapSwitcher {

	/**
	 * Open a map chooser menu
	 * @param player to choose
	 * @param match  to modify
	 * @throws Exception when wrong backend communication
	 */
	void openMapMenu(Player player, Match match) throws Exception;

}

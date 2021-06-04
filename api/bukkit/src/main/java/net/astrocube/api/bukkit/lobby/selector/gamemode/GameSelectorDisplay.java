package net.astrocube.api.bukkit.lobby.selector.gamemode;

import net.astrocube.api.core.virtual.user.User;
import org.bukkit.entity.Player;

public interface GameSelectorDisplay {

	/**
	 * Open game selector display for a certain player
	 * @param user   where data will be fetched
	 * @param player to be displayed
	 */
	void openDisplay(User user, Player player);

}

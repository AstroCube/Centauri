package net.astrocube.api.bukkit.lobby.hide;

import net.astrocube.api.core.virtual.user.User;
import org.bukkit.entity.Player;

public interface HideItemActionable {

	/**
	 * Update in-game & database logic for switching status
	 * @param user   to be updated
	 * @param player to be switched
	 */
	void switchHideStatus(User user, Player player);

}

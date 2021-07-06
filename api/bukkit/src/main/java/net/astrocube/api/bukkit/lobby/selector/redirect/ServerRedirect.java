package net.astrocube.api.bukkit.lobby.selector.redirect;

import org.bukkit.entity.Player;

public interface ServerRedirect {

	/**
	 * Redirect player to specified lobby selector
	 *
	 * @param player     to be redirected
	 * @param serverName where validations will be done
	 * @param status     to decide user switching
	 */
	void redirectPlayer(Player player, String serverName, ServerSwitchStatus status);
}
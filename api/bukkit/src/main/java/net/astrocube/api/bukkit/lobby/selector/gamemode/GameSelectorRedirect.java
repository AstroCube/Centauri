package net.astrocube.api.bukkit.lobby.selector.gamemode;

import net.astrocube.api.core.virtual.gamemode.GameMode;
import org.bukkit.entity.Player;

public interface GameSelectorRedirect {

	/**
	 * Redirect a placer to specific {@link GameMode} lobby
	 * @param gameMode where lobby will be retrieved
	 * @param player   to be redirected
	 */
	void redirectPlayer(GameMode gameMode, Player player);
}

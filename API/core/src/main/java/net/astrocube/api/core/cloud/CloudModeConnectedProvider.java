package net.astrocube.api.core.cloud;

import net.astrocube.api.core.virtual.gamemode.GameMode;

public interface CloudModeConnectedProvider {

	/**
	 * Obtain online players from a complete mode
	 * @param gameMode to be used
	 * @return count of online players
	 */
	int getGlobalOnline(GameMode gameMode);

	/**
	 * Obtain online connections from group
	 * @param group to be used
	 * @return count of online players
	 */
	int getGroupOnline(String group);

}

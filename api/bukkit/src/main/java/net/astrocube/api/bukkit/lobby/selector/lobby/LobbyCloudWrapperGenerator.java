package net.astrocube.api.bukkit.lobby.selector.lobby;

import net.astrocube.api.core.cloud.CloudInstanceProvider;
import net.astrocube.api.core.virtual.gamemode.GameMode;

import java.util.List;

public interface LobbyCloudWrapperGenerator {

	/**
	 * Get set of {@link GameMode} lobbies from cloud integration
	 * @param gameMode to search
	 * @return collection containing lobbies
	 */
	List<CloudInstanceProvider.Instance> getGameModeLobbies(GameMode gameMode);

}

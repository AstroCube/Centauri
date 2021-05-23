package net.astrocube.commons.core.cloud;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import cloud.timo.TimoCloud.api.objects.ServerGroupObject;
import cloud.timo.TimoCloud.api.objects.ServerObject;
import net.astrocube.api.core.cloud.CloudModeConnectedProvider;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CoreModeConnectedProvider implements CloudModeConnectedProvider {

	@Override
	public int getGlobalOnline(GameMode gameMode) {

		int count = 0;

		try {
			count += getGroupOnline(gameMode.getLobby());
			if (gameMode.getSubTypes() != null) {
				for (SubGameMode subGameMode : gameMode.getSubTypes()) {
					count += getGroupOnline(subGameMode.getGroup());
				}
			}
		} catch (Exception e) {
			Logger.getGlobal().log(
				Level.WARNING,
				"An error occurred while getting online player count in "
					+ " gamemode '" + gameMode.getName() + "'",
				e
			);
		}

		return count;
	}

	@Override
	public int getGroupOnline(String group) {
		ServerGroupObject lobby = TimoCloudAPI.getUniversalAPI().getServerGroup(group);
		if (lobby == null) {
			return -1;
		}
		int count = 0;
		for (ServerObject server : lobby.getServers()) {
			count += server.getOnlinePlayerCount();
		}
		return count;
	}

}

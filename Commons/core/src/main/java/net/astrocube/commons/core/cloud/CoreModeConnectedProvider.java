package net.astrocube.commons.core.cloud;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import cloud.timo.TimoCloud.api.objects.ServerGroupObject;
import cloud.timo.TimoCloud.api.objects.ServerObject;
import net.astrocube.api.core.cloud.CloudModeConnectedProvider;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;

public class CoreModeConnectedProvider implements CloudModeConnectedProvider {

	@Override
	public int getGlobalOnline(GameMode gameMode) {

		int compound = 0;

		try {

			compound += getGroupOnline(gameMode.getLobby());

			if (gameMode.getSubTypes() != null) {
				for (SubGameMode subGameMode : gameMode.getSubTypes()) {
					compound += getGroupOnline(subGameMode.getGroup());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return compound;
	}

	@Override
	public int getGroupOnline(String group) {

		int compound = 0;

		ServerGroupObject lobby = TimoCloudAPI.getUniversalAPI().getServerGroup(group);

		for (ServerObject server : lobby.getServers()) {
			compound += server.getOnlinePlayerCount();
		}

		return compound;

	}

}

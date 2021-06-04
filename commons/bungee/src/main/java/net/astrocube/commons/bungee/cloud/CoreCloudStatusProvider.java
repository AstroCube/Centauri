package net.astrocube.commons.bungee.cloud;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import cloud.timo.TimoCloud.api.objects.PlayerObject;
import com.google.inject.Singleton;
import net.astrocube.api.core.cloud.CloudStatusProvider;
import net.md_5.bungee.api.ProxyServer;

@Singleton
public class CoreCloudStatusProvider implements CloudStatusProvider {

	@Override
	public boolean hasCloudHooked() {
		return ProxyServer.getInstance().getPluginManager().getPlugin("TimoCloud") != null;
	}

	@Override
	public int getOnline() {
		return -1;
	}

	@Override
	public boolean hasAliveSession(String player) {
		return TimoCloudAPI.getUniversalAPI().getPlayer(player).isOnline();
	}

	@Override
	public String getPlayerServer(String player) {

		PlayerObject playerObject = TimoCloudAPI.getUniversalAPI().getPlayer(player);

		if (!playerObject.isOnline()) {
			return "";
		}

		return playerObject.getServer().getName();
	}

	@Override
	public void updateGameStatus(State state) {
		throw new UnsupportedOperationException("Can not set proxy status");
	}

}

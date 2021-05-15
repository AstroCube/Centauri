package net.astrocube.commons.core.cloud;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import com.google.inject.Singleton;
import net.astrocube.api.core.cloud.CloudTeleport;

import java.util.Optional;

@Singleton
public class CoreCloudTeleport implements CloudTeleport {

	@Override
	public void teleportToServer(String server, String player) {
		Optional.ofNullable(TimoCloudAPI.getUniversalAPI().getServer(server))
			.ifPresent(serverObject -> TimoCloudAPI.getUniversalAPI().getPlayer(player).sendToServer(serverObject));
	}

	@Override
	public void teleportToGroup(String group, String player) {
		getServerFromGroup(group).ifPresent(name ->
			TimoCloudAPI.getUniversalAPI().getPlayer(player)
				.sendToServer(TimoCloudAPI.getUniversalAPI().getServer(name)));
	}

	@Override
	public Optional<String> getServerFromGroup(String group) {
		return TimoCloudAPI.getUniversalAPI()
			.getServerGroups().stream()
			.filter(g -> g.getName().equalsIgnoreCase(group))
			.flatMap(g -> g.getServers().stream())
			.map(s -> s.getName().toLowerCase())
			.findAny();
	}

	@Override
	public void teleportToActual(String player) {
		TimoCloudAPI.getUniversalAPI().getPlayer(player).sendToServer(TimoCloudAPI.getBukkitAPI().getThisServer());
	}

}

package net.astrocube.lobby.listener.user;

import com.google.inject.Inject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class LobbyJoinTeleportListener implements Listener {

	@Inject
	private Plugin plugin;

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerJoinEvent event) {
		event.getPlayer()
			.teleport(
				new Location(
					Bukkit.getWorlds().get(0),
					plugin.getConfig().getInt("spawn.x", 0),
					plugin.getConfig().getInt("spawn.y", 0),
					plugin.getConfig().getInt("spawn.z", 0),
					plugin.getConfig().getInt("spawn.yaw", 0),
					plugin.getConfig().getInt("spawn.pitch", 0)
				)
			);
	}

}

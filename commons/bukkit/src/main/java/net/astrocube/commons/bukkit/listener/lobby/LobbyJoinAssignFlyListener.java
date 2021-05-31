package net.astrocube.commons.bukkit.listener.lobby;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.lobby.event.LobbyJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class LobbyJoinAssignFlyListener implements Listener {

	@Inject
	private Plugin plugin;

	@EventHandler
	public void onLobbyJoin(LobbyJoinEvent event) {

		Player player = event.getPlayer();

		Bukkit.getScheduler().runTask(plugin, () -> {
			if (player.hasPermission("commons.fly")) {
				player.setAllowFlight(true);
			}
		});
	}

}

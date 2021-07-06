package net.astrocube.lobby.selector.gamemode;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;

import net.astrocube.api.bukkit.lobby.selector.redirect.ServerRedirect;
import net.astrocube.api.bukkit.lobby.selector.redirect.ServerSwitchStatus;
import net.astrocube.api.bukkit.teleport.ServerTeleportRetry;
import net.astrocube.api.bukkit.translation.mode.AlertModes;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CoreServerRedirect implements ServerRedirect {

	@Inject
	private MessageHandler messageHandler;
	@Inject
	private ServerTeleportRetry serverTeleportRetry;

	@Override
	public void redirectPlayer(Player player, String serverName, ServerSwitchStatus status) {
		switch (status) {
			case FULL: {
				messageHandler.sendIn(player, AlertModes.ERROR, "lobby.error.full");
				break;
			}
			case CYCLIC: {
				messageHandler.sendIn(player, AlertModes.ERROR, "lobby.error.cyclic");
				player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
				break;
			}
			default: {
				serverTeleportRetry.attemptTeleport(player.getName(), serverName, 1, 3);
				break;
			}
		}
	}
}
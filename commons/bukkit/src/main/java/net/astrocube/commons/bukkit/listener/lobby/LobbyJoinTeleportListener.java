package net.astrocube.commons.bukkit.listener.lobby;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.lobby.event.LobbyJoinEvent;
import net.astrocube.commons.bukkit.game.match.lobby.LobbyLocationParser;
import net.astrocube.commons.bukkit.utils.TeleportUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class LobbyJoinTeleportListener implements Listener {

	@Inject
	private Plugin plugin;

	@EventHandler
	public void onJoinLobby(LobbyJoinEvent event) {
		TeleportUtils.loadChunkAndTeleport(event.getPlayer(), LobbyLocationParser.getLobby(), plugin);
	}

}

package net.astrocube.commons.bukkit.listener.lobby;

import net.astrocube.api.bukkit.lobby.event.LobbyJoinEvent;
import net.astrocube.commons.bukkit.game.match.lobby.LobbyLocationParser;
import net.astrocube.commons.bukkit.utils.TeleportUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LobbyJoinTeleportListener implements Listener {

	@EventHandler
	public void onJoinLobby(LobbyJoinEvent event) {
		TeleportUtils.loadChunkAndTeleport(event.getPlayer(), LobbyLocationParser.getLobby());
	}

}

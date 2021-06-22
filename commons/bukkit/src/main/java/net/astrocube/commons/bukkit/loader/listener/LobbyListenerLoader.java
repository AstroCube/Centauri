package net.astrocube.commons.bukkit.loader.listener;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.server.ListenerLoader;
import net.astrocube.commons.bukkit.game.spectator.LobbyActionListener;
import net.astrocube.commons.bukkit.listener.game.session.LobbyUserDisconnectListener;
import net.astrocube.commons.bukkit.listener.game.spectator.LobbyReturnListener;
import net.astrocube.commons.bukkit.listener.lobby.LobbyJoinAssignFlyListener;
import net.astrocube.commons.bukkit.listener.lobby.LobbyJoinTeleportListener;
import org.bukkit.plugin.Plugin;

public class LobbyListenerLoader implements ListenerLoader {

	private @Inject Plugin plugin;

	private @Inject LobbyUserDisconnectListener lobbyUserDisconnectListener;
	private @Inject LobbyReturnListener lobbyReturnListener;
	private @Inject LobbyActionListener lobbyActionListener;
	private @Inject LobbyJoinAssignFlyListener lobbyJoinAssignFlyListener;
	private @Inject LobbyJoinTeleportListener lobbyJoinTeleportListener;

	@Override
	public void registerEvents() {
		registerEvent(
			plugin,
			lobbyUserDisconnectListener,
			lobbyReturnListener,
			lobbyActionListener,
			lobbyJoinAssignFlyListener
			//lobbyJoinTeleportListener
		);
	}
}
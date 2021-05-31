package net.astrocube.commons.bukkit.nametag.lobby;

import net.astrocube.api.bukkit.nametag.types.lobby.LobbyNametag;
import net.astrocube.api.bukkit.nametag.types.lobby.RenderedLobbyNametag;
import net.astrocube.commons.bukkit.nametag.SimpleRenderedNametag;
import org.bukkit.entity.Player;

import java.util.Set;

public class CoreRenderedLobbyNametag extends SimpleRenderedNametag implements RenderedLobbyNametag {

	public CoreRenderedLobbyNametag(LobbyNametag nametag, Set<Entity> spawnedEntities, Player viewer) {
		super(nametag, spawnedEntities, viewer);
	}
}
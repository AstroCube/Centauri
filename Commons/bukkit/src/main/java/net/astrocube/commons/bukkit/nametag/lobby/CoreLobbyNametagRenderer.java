package net.astrocube.commons.bukkit.nametag.lobby;

import net.astrocube.api.bukkit.nametag.Nametag;
import net.astrocube.api.bukkit.nametag.types.lobby.LobbyNametag;
import net.astrocube.api.bukkit.nametag.types.lobby.LobbyNametagRenderer;
import net.astrocube.api.bukkit.nametag.types.lobby.RenderedLobbyNametag;
import net.astrocube.commons.bukkit.nametag.RenderUtil;
import org.bukkit.entity.Player;

import java.util.Set;

public class CoreLobbyNametagRenderer implements LobbyNametagRenderer {

	@Override
	public RenderedLobbyNametag render(LobbyNametag nametag, Player player) {
		Set<Nametag.Rendered.Entity> spawnedEntities = RenderUtil.getEntities(nametag);
		RenderedLobbyNametag lobbyNametag = new CoreRenderedLobbyNametag(nametag, spawnedEntities, player);
		lobbyNametag.show();

		return lobbyNametag;
	}
}
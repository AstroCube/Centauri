package net.astrocube.lobby.gamemode;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.lobby.gamemode.LobbyModeProvider;

public class GameModeModule extends ProtectedModule {

	@Override
	public void configure() {
		bind(LobbyModeProvider.class).to(CoreLobbyModeProvider.class);
	}
}

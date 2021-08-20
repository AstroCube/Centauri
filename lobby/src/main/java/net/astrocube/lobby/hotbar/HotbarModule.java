package net.astrocube.lobby.hotbar;

import net.astrocube.inject.ProtectedModule;
import net.astrocube.api.bukkit.lobby.hotbar.LobbyHotbarProvider;

public class HotbarModule extends ProtectedModule {
	@Override
	public void configure() {
		bind(LobbyHotbarProvider.class).to(CoreLobbyHotbarProvider.class);
	}
}

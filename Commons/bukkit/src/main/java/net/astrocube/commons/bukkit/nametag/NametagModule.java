package net.astrocube.commons.bukkit.nametag;

import com.google.inject.Scopes;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.nametag.NametagRegistry;
import net.astrocube.api.bukkit.nametag.types.lobby.LobbyNametagRenderer;
import net.astrocube.commons.bukkit.nametag.lobby.CoreLobbyNametagRenderer;

public class NametagModule extends ProtectedModule {

	@Override
	protected void configure() {
		bind(NametagRegistry.class).to(DefaultNametagRegistry.class).in(Scopes.SINGLETON);
		bind(LobbyNametagRenderer.class).to(CoreLobbyNametagRenderer.class).in(Scopes.SINGLETON);
		expose(NametagRegistry.class);
		expose(LobbyNametagRenderer.class);
	}
}
package net.astrocube.commons.bukkit.game.match.control.menu;

import net.astrocube.inject.ProtectedModule;
import net.astrocube.api.bukkit.game.match.control.menu.MatchLobbyMenuProvider;
import net.astrocube.api.bukkit.game.match.control.menu.MatchMapSwitcher;
import net.astrocube.api.bukkit.game.match.control.menu.MatchPrivatizeSwitcher;

public class ControlLobbyModule extends ProtectedModule {

	@Override
	public void configure() {
		bind(MatchLobbyMenuProvider.class).to(CoreMatchLobbyMenuProvider.class);
		bind(MatchPrivatizeSwitcher.class).to(CoreMatchPrivatizeSwitcher.class);
		bind(MatchMapSwitcher.class).to(CoreMatchMapSwitcher.class);
	}
}

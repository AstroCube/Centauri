package net.astrocube.lobby.selector;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.lobby.selector.gamemode.GameItemExtractor;
import net.astrocube.api.bukkit.lobby.selector.gamemode.GameSelectorDisplay;
import net.astrocube.api.bukkit.lobby.selector.gamemode.GameSelectorRedirect;
import net.astrocube.api.bukkit.lobby.selector.lobby.LobbyCloudWrapperGenerator;
import net.astrocube.api.bukkit.lobby.selector.lobby.LobbyIconExtractor;
import net.astrocube.api.bukkit.lobby.selector.lobby.LobbySelectorDisplay;
import net.astrocube.api.bukkit.lobby.selector.lobby.LobbyServerRedirect;
import net.astrocube.lobby.selector.gamemode.CoreGameItemExtractor;
import net.astrocube.lobby.selector.gamemode.CoreGameSelectorDisplay;
import net.astrocube.lobby.selector.gamemode.CoreGameSelectorRedirect;
import net.astrocube.lobby.selector.lobby.CoreLobbyCloudWrapperGenerator;
import net.astrocube.lobby.selector.lobby.CoreLobbyIconExtractor;
import net.astrocube.lobby.selector.lobby.CoreLobbySelectorDisplay;
import net.astrocube.lobby.selector.lobby.CoreLobbyServerRedirect;

public class SelectorModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(GameItemExtractor.class).to(CoreGameItemExtractor.class);
        bind(GameSelectorDisplay.class).to(CoreGameSelectorDisplay.class);
        bind(GameSelectorRedirect.class).to(CoreGameSelectorRedirect.class);
        bind(LobbySelectorDisplay.class).to(CoreLobbySelectorDisplay.class);
        bind(LobbyIconExtractor.class).to(CoreLobbyIconExtractor.class);
        bind(LobbyServerRedirect.class).to(CoreLobbyServerRedirect.class);
        bind(LobbyCloudWrapperGenerator.class).to(CoreLobbyCloudWrapperGenerator.class);
    }
}

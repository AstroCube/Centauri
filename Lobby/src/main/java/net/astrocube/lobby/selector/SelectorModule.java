package net.astrocube.lobby.selector;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.lobby.selector.gamemode.GameItemExtractor;
import net.astrocube.api.bukkit.lobby.selector.gamemode.GameSelectorDisplay;
import net.astrocube.lobby.selector.gamemode.CoreGameItemExtractor;
import net.astrocube.lobby.selector.gamemode.CoreGameSelectorDisplay;

public class SelectorModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(GameItemExtractor.class).to(CoreGameItemExtractor.class);
        bind(GameSelectorDisplay.class).to(CoreGameSelectorDisplay.class);
    }
}

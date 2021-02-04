package net.astrocube.lobby.loader;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.translation.TranslationModule;
import net.astrocube.lobby.board.ScoreboardModule;
import net.astrocube.lobby.gamemode.GameModeModule;
import net.astrocube.lobby.hide.HideModule;
import net.astrocube.lobby.hotbar.HotbarModule;
import net.astrocube.lobby.nametag.NametagModule;
import net.astrocube.lobby.selector.SelectorModule;

public class InjectionLoaderModule extends ProtectedModule {

    @Override
    public void configure() {
        install(new LoaderModule());
        install(new ScoreboardModule());
        install(new NametagModule());
        install(new TranslationModule());
        install(new GameModeModule());
        install(new SelectorModule());
        install(new HideModule());
        install(new HotbarModule());
    }

}

package net.astrocube.lobby.loader;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.lobby.LobbyModule;
import net.astrocube.lobby.hide.HideModule;
import net.astrocube.lobby.hotbar.HotbarModule;

public class InjectionLoaderModule extends ProtectedModule {

    @Override
    public void configure() {
        install(new LoaderModule());
        install(new HideModule());
        install(new HotbarModule());
    }

}

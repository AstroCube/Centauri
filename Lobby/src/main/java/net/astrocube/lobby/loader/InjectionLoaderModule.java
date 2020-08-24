package net.astrocube.lobby.loader;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.lobby.hide.HideModule;

public class InjectionLoaderModule extends ProtectedModule {

    @Override
    public void configure() {
        install(new HideModule());
    }

}

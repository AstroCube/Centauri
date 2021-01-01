package net.astrocube.commons.bungee.loader;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.loader.Loader;

public class LoaderModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(Loader.class).to(CommonsLoader.class);
    }

}

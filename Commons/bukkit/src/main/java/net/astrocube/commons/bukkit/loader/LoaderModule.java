package net.astrocube.commons.bukkit.loader;

import com.google.inject.name.Names;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.commons.core.inject.ProtectedModule;

public class LoaderModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(Loader.class).to(BukkitLoader.class);
        bind(Loader.class).annotatedWith(Names.named("server")).to(ServerLoader.class);
        bind(Loader.class).annotatedWith(Names.named("events")).to(EventListenerLoader.class);
    }

}

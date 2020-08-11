package net.astrocube.commons.bukkit.loader;

import com.google.inject.name.Names;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.loader.Loader;

public class LoaderModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(Loader.class).to(BukkitLoader.class);
        bind(Loader.class).annotatedWith(Names.named("server")).to(ServerLoader.class);
        bind(Loader.class).annotatedWith(Names.named("events")).to(EventListenerLoader.class);
    }

}

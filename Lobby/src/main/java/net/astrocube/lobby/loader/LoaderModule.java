package net.astrocube.lobby.loader;

import com.google.inject.name.Names;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.lobby.inject.NPCLibProvider;

public class LoaderModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(Loader.class).to(LobbyLoader.class);
        bind(Loader.class).annotatedWith(Names.named("events")).to(EventListenerLoader.class);
        bind(Loader.class).annotatedWith(Names.named("world")).to(WorldLoader.class);
    }

}

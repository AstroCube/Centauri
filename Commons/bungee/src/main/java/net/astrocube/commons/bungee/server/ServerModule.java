package net.astrocube.commons.bungee.server;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.server.ServerStartResolver;

public class ServerModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(ServerStartResolver.class).to(BungeeStartResolver.class);
    }

}

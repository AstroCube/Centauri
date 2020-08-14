package net.astrocube.commons.bukkit.server;

import net.astrocube.api.bukkit.session.net.seocraft.api.bukkit.server.ServerDisconnectHandler;
import net.astrocube.api.core.server.ServerStartResolver;
import net.astrocube.commons.core.inject.ProtectedModule;

public class ServerModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(ServerStartResolver.class).to(BukkitStartResolver.class);
        bind(ServerDisconnectHandler.class).to(CoreServerDisconnectHandler.class);
    }

}

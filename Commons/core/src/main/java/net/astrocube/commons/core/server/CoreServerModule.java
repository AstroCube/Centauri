package net.astrocube.commons.core.server;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.server.GameServerStartManager;
import net.astrocube.api.core.server.ServerService;
import net.astrocube.api.core.server.ServerConnectionManager;

public class CoreServerModule extends ProtectedModule {

    @Override
    protected void configure() {
        bind(ServerConnectionManager.class).to(CentauriConnectionManager.class);
        bind(GameServerStartManager.class).to(CentauriGameStartManager.class);
        bind(ServerService.class).to(ServerModelService.class);
    }

}

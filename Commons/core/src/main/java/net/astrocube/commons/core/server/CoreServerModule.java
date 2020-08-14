package net.astrocube.commons.core.server;

import net.astrocube.api.core.server.GameServerStartManager;
import net.astrocube.api.core.server.ServerService;
import net.astrocube.api.core.server.ServerConnectionManager;
import net.astrocube.commons.core.inject.ProtectedModule;

public class CoreServerModule extends ProtectedModule {

    @Override
    protected void configure() {
        bind(ServerConnectionManager.class).to(CentauriConnectionManager.class);
        bind(GameServerStartManager.class).to(CentauriGameStartManager.class);
        bind(ServerService.class).to(ServerModelService.class);
    }

}

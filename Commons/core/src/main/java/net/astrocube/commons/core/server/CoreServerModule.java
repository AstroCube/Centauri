package net.astrocube.commons.core.server;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.message.ChannelBinder;
import net.astrocube.api.core.message.MessageDefaults;
import net.astrocube.api.core.server.GameServerStartManager;
import net.astrocube.api.core.server.ServerAliveMessage;
import net.astrocube.api.core.server.ServerConnectionManager;
import net.astrocube.api.core.server.ServerService;

public class CoreServerModule extends ProtectedModule implements ChannelBinder {

    @Override @MessageDefaults.ChannelName("serverPing")
    protected void configure() {
        bind(ServerConnectionManager.class).to(CentauriConnectionManager.class);
        bind(GameServerStartManager.class).to(CentauriGameStartManager.class);
        bind(ServerService.class).to(ServerModelService.class);
        expose(ServerConnectionManager.class);

        bindChannel(ServerAliveMessage.class);
    }


}

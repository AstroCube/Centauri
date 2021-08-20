package net.astrocube.commons.bukkit.server;

import net.astrocube.inject.ProtectedModule;
import net.astrocube.api.bukkit.server.ServerDisconnectHandler;

import net.astrocube.api.core.message.ChannelBinder;
import net.astrocube.api.core.server.ServerStartResolver;

public class ServerModule extends ProtectedModule implements ChannelBinder {

	@Override
	public void configure() {
		bind(ServerStartResolver.class).to(BukkitStartResolver.class);
		bind(ServerDisconnectHandler.class).to(CoreServerDisconnectHandler.class);
	}

}

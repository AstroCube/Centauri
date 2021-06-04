package net.astrocube.commons.bukkit.server;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.server.ServerDisconnectHandler;
import net.astrocube.api.core.server.ServerStartResolver;

public class ServerModule extends ProtectedModule {

	@Override
	public void configure() {
		bind(ServerStartResolver.class).to(BukkitStartResolver.class);
		bind(ServerDisconnectHandler.class).to(CoreServerDisconnectHandler.class);
	}

}

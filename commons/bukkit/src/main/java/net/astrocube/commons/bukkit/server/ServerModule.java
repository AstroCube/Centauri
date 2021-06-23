package net.astrocube.commons.bukkit.server;

import com.google.inject.Scopes;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.server.ServerDisconnectHandler;
import net.astrocube.api.bukkit.server.broadcast.GlobalBroadcaster;
import net.astrocube.api.core.message.ChannelBinder;
import net.astrocube.api.core.server.ServerStartResolver;
import net.astrocube.commons.bukkit.server.broadcast.BroadcastMessage;
import net.astrocube.commons.bukkit.server.broadcast.BroadcasterMessageListener;
import net.astrocube.commons.bukkit.server.broadcast.CoreGlobalBroadcaster;

public class ServerModule extends ProtectedModule implements ChannelBinder {

	@Override
	public void configure() {
		bind(ServerStartResolver.class).to(BukkitStartResolver.class);
		bind(ServerDisconnectHandler.class).to(CoreServerDisconnectHandler.class);

		bind(GlobalBroadcaster.class).to(CoreGlobalBroadcaster.class).in(Scopes.SINGLETON);

		bindChannel(BroadcastMessage.class)
			.registerListener(BroadcasterMessageListener.class);

	}

}

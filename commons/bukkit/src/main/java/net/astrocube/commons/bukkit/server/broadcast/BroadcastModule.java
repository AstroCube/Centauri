package net.astrocube.commons.bukkit.server.broadcast;

import net.astrocube.inject.ProtectedModule;
import net.astrocube.api.bukkit.server.broadcast.GlobalBroadcaster;
import net.astrocube.api.core.message.ChannelBinder;

public class BroadcastModule extends ProtectedModule implements ChannelBinder {

	protected void configure() {
		bindChannel(BroadcastMessage.class)
			.registerListener(BroadcasterMessageListener.class);

		bind(GlobalBroadcaster.class).to(CoreGlobalBroadcaster.class);

	}
}

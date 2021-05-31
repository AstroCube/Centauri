package net.astrocube.commons.bungee.player;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.message.ChannelBinder;
import net.astrocube.api.core.player.ProxyKickRequest;

public class PlayerModule extends ProtectedModule implements ChannelBinder {

	@Override
	public void configure() {
		bindChannel(ProxyKickRequest.class)
			.registerListener(PlayerKickRequestHandler.class);
	}

}

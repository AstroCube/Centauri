package net.astrocube.commons.bungee.player;

import net.astrocube.api.core.message.MessageListener;
import net.astrocube.api.core.message.MessageMetadata;
import net.astrocube.api.core.player.ProxyKickRequest;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PlayerKickRequestHandler implements MessageListener<ProxyKickRequest> {

	@Override
	public void handleDelivery(ProxyKickRequest message, MessageMetadata properties) {

		ProxiedPlayer player = ProxyServer.getInstance().getPlayer(message.getName());

		if (player != null) {
			player.disconnect(new TextComponent(message.getReason()));
		}

	}
}

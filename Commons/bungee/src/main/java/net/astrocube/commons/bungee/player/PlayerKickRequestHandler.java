package net.astrocube.commons.bungee.player;

import net.astrocube.api.core.message.MessageHandler;
import net.astrocube.api.core.message.Metadata;
import net.astrocube.api.core.player.ProxyKickRequest;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PlayerKickRequestHandler implements MessageHandler<ProxyKickRequest> {

	@Override
	public Class<ProxyKickRequest> type() {
		return ProxyKickRequest.class;
	}


	@Override
	public void handleDelivery(ProxyKickRequest message, Metadata properties) {

		ProxiedPlayer player = ProxyServer.getInstance().getPlayer(message.getName());

		if (player != null) {
			player.disconnect(new TextComponent(message.getReason()));
		}

	}
}

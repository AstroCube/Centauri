package net.astrocube.commons.bukkit.server.broadcast;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.core.message.MessageListener;
import net.astrocube.api.core.message.MessageMetadata;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BroadcasterMessageListener implements MessageListener<BroadcastMessage> {

	@Inject
	private MessageHandler messageHandler;

	@Override
	public void handleDelivery(BroadcastMessage message, MessageMetadata properties) {
		Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(getPrefix(player) + message.getMessage()));
	}

	private String getPrefix(Player player) {
		return messageHandler.get(player , "prefix");
	}

}

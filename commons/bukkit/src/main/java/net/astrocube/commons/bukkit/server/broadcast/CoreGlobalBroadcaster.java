package net.astrocube.commons.bukkit.server.broadcast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.server.broadcast.GlobalBroadcaster;
import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.message.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CoreGlobalBroadcaster implements GlobalBroadcaster {

	private final Channel<BroadcastMessage> broadcastMessageChannel;
	private final MessageHandler messageHandler;

	@Inject
	public CoreGlobalBroadcaster(Messenger messenger, MessageHandler messageHandler) {
		broadcastMessageChannel = messenger.getChannel(BroadcastMessage.class);
		this.messageHandler = messageHandler;
	}

	@Override
	public void broadcastMessage(String message) throws JsonProcessingException {
		broadcastMessageChannel
			.sendMessage(new BroadcastMessage(message), new HashMap<>());
		broadcastMessageInServer(message);
	}

	@Override
	public void broadcastMessageInServer(String message) {
		Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(getPrefix(player) + message));
	}

	private String getPrefix(Player player) {
		return messageHandler.get(player , "prefix");
	}

}

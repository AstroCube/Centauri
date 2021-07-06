package net.astrocube.commons.bukkit.server.broadcast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import net.astrocube.api.bukkit.server.broadcast.GlobalBroadcaster;
import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.message.Messenger;

import java.util.HashMap;

public class CoreGlobalBroadcaster implements GlobalBroadcaster {

	private final Channel<BroadcastMessage> broadcastMessageChannel;
	private final BroadcastSender sender;

	@Inject
	public CoreGlobalBroadcaster(Messenger messenger, BroadcastSender sender) {
		broadcastMessageChannel = messenger.getChannel(BroadcastMessage.class);
		this.sender = sender;
	}

	@Override
	public void broadcastMessage(String message) throws JsonProcessingException {
		broadcastMessageChannel
			.sendMessage(new BroadcastMessage(message), new HashMap<>());
		sender.sendToServer(message);
	}

}
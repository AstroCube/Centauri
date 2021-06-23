package net.astrocube.commons.bukkit.server.broadcast;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.server.broadcast.GlobalBroadcaster;
import net.astrocube.api.core.message.MessageListener;
import net.astrocube.api.core.message.MessageMetadata;

public class BroadcasterMessageListener implements MessageListener<BroadcastMessage> {

	@Inject
	private GlobalBroadcaster globalBroadcaster;

	@Override
	public void handleDelivery(BroadcastMessage message, MessageMetadata properties) {
		globalBroadcaster.broadcastMessageInServer(message.getMessage());
	}

}

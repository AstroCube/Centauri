package net.astrocube.commons.core.server;

import com.google.inject.Inject;
import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.message.MessageListener;
import net.astrocube.api.core.message.Messenger;
import net.astrocube.api.core.message.MessageMetadata;
import net.astrocube.api.core.server.ServerAliveMessage;
import net.astrocube.api.core.server.ServerService;

import java.util.HashMap;

public class AliveInterceptorHandler implements MessageListener<ServerAliveMessage> {

	private @Inject ServerService serverService;
	private final Channel<ServerAliveMessage> channel;

	@Inject
	public AliveInterceptorHandler(Messenger messenger) {
		channel = messenger.getChannel(ServerAliveMessage.class);
	}

	@Override
	public void handleDelivery(ServerAliveMessage message, MessageMetadata properties) {

		try {

			String actual = serverService.getActual().getId();

			if (message.getAction() == ServerAliveMessage.Action.REQUEST) {
				channel.sendMessage(
					new ServerAliveMessage(actual, ServerAliveMessage.Action.CONFIRM),
					new HashMap<>()
				);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

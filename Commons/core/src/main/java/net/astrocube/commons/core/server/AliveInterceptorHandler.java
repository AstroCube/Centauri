package net.astrocube.commons.core.server;

import com.google.inject.Inject;
import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.message.MessageHandler;
import net.astrocube.api.core.message.Messenger;
import net.astrocube.api.core.message.Metadata;
import net.astrocube.api.core.server.ServerAliveMessage;
import net.astrocube.api.core.server.ServerService;

import java.util.HashMap;

public class AliveInterceptorHandler implements MessageHandler<ServerAliveMessage> {

	private @Inject ServerService serverService;
	private final Channel<ServerAliveMessage> channel;

	@Inject
	public AliveInterceptorHandler(Messenger messenger) {
		channel = messenger.getChannel(ServerAliveMessage.class);
	}

	@Override
	public Class<ServerAliveMessage> type() {
		return ServerAliveMessage.class;
	}

	@Override
	public void handleDelivery(ServerAliveMessage message, Metadata properties) {

		try {

			String actual = serverService.getActual().getId();

			if (message.getAction() == ServerAliveMessage.Action.REQUEST) {

				channel.sendMessage(
					new ServerAliveMessage() {
						@Override
						public String getServer() {
							return actual;
						}

						@Override
						public Action getAction() {
							return Action.CONFIRM;
						}
					},
					new HashMap<>()
				);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

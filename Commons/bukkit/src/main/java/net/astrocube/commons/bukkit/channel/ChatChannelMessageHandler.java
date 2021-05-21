package net.astrocube.commons.bukkit.channel;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.astrocube.api.bukkit.channel.InterceptorRegistry;
import net.astrocube.api.bukkit.virtual.channel.ChatChannel;
import net.astrocube.api.bukkit.virtual.channel.ChatChannelMessage;
import net.astrocube.api.core.message.MessageListener;
import net.astrocube.api.core.message.MessageMetadata;

import javax.inject.Inject;
import java.io.IOException;

public class ChatChannelMessageHandler implements MessageListener<ChatChannelMessage> {

	private @Inject InterceptorRegistry interceptorRegistry;
	private @Inject ObjectMapper mapper;

	@Override
	public void handleDelivery(ChatChannelMessage message, MessageMetadata properties) {
		try {
			ChatChannel channel = this.mapper.readValue((String) properties.getHeaders().get("channel"), ChatChannel.class);
			this.interceptorRegistry.getInterceptors().forEach(interceptor -> interceptor.intercept(channel, message));
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
}
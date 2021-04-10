package net.astrocube.commons.bukkit.channel;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.astrocube.api.bukkit.channel.InterceptorRegistry;
import net.astrocube.api.bukkit.virtual.channel.ChatChannel;
import net.astrocube.api.bukkit.virtual.channel.ChatChannelMessage;
import net.astrocube.api.core.message.MessageHandler;
import net.astrocube.api.core.message.Metadata;

import javax.inject.Inject;
import java.io.IOException;

public class ChatChannelMessageHandler implements MessageHandler<ChatChannelMessage> {

    private @Inject InterceptorRegistry interceptorRegistry;
    private @Inject ObjectMapper mapper;

    @Override
    public Class<ChatChannelMessage> type() {
        return ChatChannelMessage.class;
    }

    @Override
    public void handleDelivery(ChatChannelMessage message, Metadata properties) {
        try {
            ChatChannel channel = this.mapper.readValue((String) properties.getHeaders().get("channel"), ChatChannel.class);
            this.interceptorRegistry.getInterceptors().forEach(interceptor -> interceptor.intercept(channel, message));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
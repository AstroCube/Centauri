package net.astrocube.commons.bukkit.channel;

import net.astrocube.api.bukkit.channel.InterceptorRegistry;
import net.astrocube.api.bukkit.virtual.channel.ChannelMessage;
import net.astrocube.api.core.message.MessageHandler;
import net.astrocube.api.core.message.Metadata;

import javax.inject.Inject;

public class ChannelMessageHandler implements MessageHandler<ChannelMessage> {

    private @Inject InterceptorRegistry interceptorRegistry;

    @Override
    public Class<ChannelMessage> type() {
        return ChannelMessage.class;
    }

    @Override
    public void handleDelivery(ChannelMessage message, Metadata properties) {
        this.interceptorRegistry.getInterceptors().forEach(interceptor -> interceptor.intercept(message));
    }
}
package net.astrocube.api.bukkit.channel;

public interface HandlerRegistry {

    ChannelMessageInterceptor getHandler(String channelName);

}
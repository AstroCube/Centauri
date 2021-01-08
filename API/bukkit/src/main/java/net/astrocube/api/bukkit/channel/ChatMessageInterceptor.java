package net.astrocube.api.bukkit.channel;

import net.astrocube.api.bukkit.virtual.channel.ChatChannelMessage;

public interface MessageInterceptor {

    void intercept(ChatChannelMessage message);

}
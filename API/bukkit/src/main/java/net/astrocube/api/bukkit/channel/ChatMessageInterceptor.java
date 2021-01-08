package net.astrocube.api.bukkit.channel;

import net.astrocube.api.bukkit.virtual.channel.ChatChannel;
import net.astrocube.api.bukkit.virtual.channel.ChatChannelMessage;

public interface ChatMessageInterceptor {

    void intercept(ChatChannel channel, ChatChannelMessage message);

}
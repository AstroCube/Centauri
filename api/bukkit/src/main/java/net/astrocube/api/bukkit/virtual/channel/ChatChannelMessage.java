package net.astrocube.api.bukkit.virtual.channel;

import net.astrocube.api.core.message.Message;
import net.astrocube.api.core.model.ModelProperties;

@Message.ChannelName("channel-message")
@ModelProperties.RouteKey("channel-message")
public interface ChatChannelMessage extends ChatChannelMessageDoc.Complete, Message {
}
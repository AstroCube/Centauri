package net.astrocube.api.bukkit.virtual.channel;

import net.astrocube.api.core.message.Message;
import net.astrocube.api.core.message.MessageDefaults;
import net.astrocube.api.core.model.ModelProperties;

@MessageDefaults.ChannelName("channel-message")
@ModelProperties.RouteKey("channel-message")
public interface ChannelMessage extends ChannelMessageDoc.Complete, Message {}
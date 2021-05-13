package net.astrocube.api.bukkit.virtual.channel;

import net.astrocube.api.core.model.ModelProperties;

@ModelProperties.Cache(600)
@ModelProperties.RouteKey("channel")
public interface ChatChannel extends ChatChannelDoc.Complete {
}
package net.astrocube.api.core.service;

import net.astrocube.api.core.message.Message;
import net.astrocube.api.core.message.MessageDefaults;
import net.astrocube.api.core.model.PartialModel;

@MessageDefaults.ChannelName("model-message")
@SuppressWarnings("UnstableApiUsage")
public interface ModelRequest<Complete extends PartialModel> extends Message {
}
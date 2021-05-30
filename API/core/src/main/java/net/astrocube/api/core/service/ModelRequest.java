package net.astrocube.api.core.service;

import net.astrocube.api.core.message.Message;
import net.astrocube.api.core.model.PartialModel;

@Message.ChannelName("model-message")
public interface ModelRequest<Complete extends PartialModel> extends Message {
}
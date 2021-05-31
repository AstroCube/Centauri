package net.astrocube.api.core.message;

public interface MessageListener<T extends Message> {

	void handleDelivery(T message, MessageMetadata properties);

}
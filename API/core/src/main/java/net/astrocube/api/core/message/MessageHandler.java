package net.astrocube.api.core.message;

public interface MessageHandler<T extends Message> {

    Class<T> type();

    void handleDelivery(T message, Metadata properties);

}
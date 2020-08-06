package net.astrocube.api.core.message;

public interface Messenger {

    <T extends Message> Channel<T> getChannel(Class<T> type);

}
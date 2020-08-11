package net.astrocube.api.core.message;

import com.google.common.reflect.TypeToken;

import javax.annotation.Nullable;
import java.util.concurrent.Executor;

public interface MessageQueue {

    /**
     * Tell the queue to receive messages of the given type
     */
    void bind(Class<? extends Message> type);

    <T extends Message> void subscribe(TypeToken<T> messageType, MessageHandler<T> handler, @Nullable Executor executor);

    default <T extends Message> void subscribe(Class<T> messageType, MessageHandler<T> handler, @Nullable Executor executor) {
        subscribe(TypeToken.of(messageType), handler, executor);
    }

    default <T extends Message> void subscribe(TypeToken<T> messageType, MessageHandler<T> handler) {
        subscribe(messageType, handler, null);
    }

    default <T extends Message> void subscribe(Class<T> messageType, MessageHandler<T> handler) {
        subscribe(messageType, handler, null);
    }

    void subscribe(MessageListener listener, @Nullable Executor executor);

    default void subscribe(MessageListener listener) {
        subscribe(listener, null);
    }

    void unsubscribe(MessageHandler<?> handler);

    void unsubscribe(MessageListener listener);
}
package net.astrocube.api.core.message;

import com.google.common.reflect.TypeToken;

import java.util.Map;
import java.util.Set;

@SuppressWarnings("UnstableApiUsage")
public interface Channel<T extends Message> {

    String getName();

    String getId();

    TypeToken<T> getType();

    Channel<T> sendMessage(T object, Map<String, Object> headers);

    Channel<T> addHandler(MessageHandler<T> handler);

    Channel<T> removeHandler(MessageHandler<T> handler);

    Set<MessageHandler<T>> getHandlers();

}
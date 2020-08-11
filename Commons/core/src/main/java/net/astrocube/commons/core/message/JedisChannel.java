package net.astrocube.commons.core.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jdk.vm.ci.meta.Local;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.message.Message;
import net.astrocube.api.core.message.MessageHandler;
import net.astrocube.api.core.message.Metadata;
import org.joda.time.DateTime;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Getter
@AllArgsConstructor
@SuppressWarnings("UnstableApiUsage")
public class JedisChannel<T extends Message> implements Channel<T> {

    private final String name;
    private final String id;
    private final TypeToken<T> type;
    private final JedisPool jedisPool;
    private final Set<MessageHandler<T>> handlers = new HashSet<>();
    private final ObjectMapper mapper;

    @Override
    public Channel<T> sendMessage(T object, Map<String, Object> headers) throws JsonProcessingException {

        String meta = mapper.writeValueAsString(new Metadata() {
            @Override
            public Map<String, Object> getHeaders() {
                return headers;
            }

            @Override
            public String getMessageId() {
                return UUID.randomUUID().toString();
            }

            @Override
            public String getAppId() {
                return name;
            }

            @Override
            public String getInstanceId() {
                return id;
            }

            @Override
            public DateTime getTimestamp() {
                return DateTime.now();
            }
        });

        ObjectNode message = mapper.createObjectNode();

        message.put("metadata", meta);
        message.put("message", mapper.writeValueAsString(object));

        try (Jedis jedis = jedisPool.getResource()) {
            jedis.publish("centauri_redis", mapper.writeValueAsString(message));
        }

        return this;
    }

    @Override
    public Channel<T> addHandler(MessageHandler<T> handler) {
        handlers.add(handler);
        return this;
    }

    @Override
    public Channel<T> removeHandler(MessageHandler<T> handler) {
        while (handlers.contains(handler)) {
            handlers.remove(handler);
        }
        return this;
    }

    public void callListeners(T object, Metadata metadata) {
        for (MessageHandler<T> listener : handlers) {
            listener.handleDelivery(object, metadata);
        }
    }

}
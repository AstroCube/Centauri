package net.astrocube.commons.core.message;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.reflect.TypeToken;
import com.google.common.util.concurrent.ListeningExecutorService;
import net.astrocube.api.core.message.*;
import net.astrocube.api.core.redis.Redis;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import java.util.*;
import java.util.concurrent.ExecutorService;

@SuppressWarnings("All")
public class JedisMessenger implements Messenger {

    private final ObjectMapper mapper;
    private final JedisPool messengerPool;
    private final Jedis listenerConnection;
    private final JedisPubSub pubSub;
    private final Map<Class<? extends Message>, JedisChannel<? extends Message>> channels;

    public JedisMessenger(Redis redis,
                          ObjectMapper mapper,
                          ExecutorService executorService,
                          Set<ChannelMeta> channelMetas,
                          Set<MessageHandler> handlers
    ) {
        this.mapper = mapper;
        this.listenerConnection = redis.getListenerConnection();
        this.messengerPool = redis.getRawConnection();
        this.channels = new HashMap<>();
        this.pubSub = new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                try {
                    JsonNode jsonMessage = (ObjectNode) mapper.readTree(message);

                    if (jsonMessage.get("metadata") != null) {
                        Metadata metadata = mapper.readValue(jsonMessage.get("metadata").toString(), Metadata.class);

                        Optional<JedisChannel<? extends Message>> channelOptional =
                                channels.values().stream()
                                        .filter(meta -> metadata.getAppId().equals(meta.getName()))
                                        .findFirst();

                        if (!channelOptional.isPresent()) {
                            return;
                        }

                        JedisChannel channelObject = channelOptional.get();

                        if (metadata.getInstanceId().equals(channelObject.getId())) {
                            return;
                        }

                        Message messageObject = (Message) mapper.readValue(
                                mapper.writeValueAsString(jsonMessage.get("message")),
                                channelObject.getType().getRawType()
                        );

                        channelObject.callListeners(messageObject, metadata);
                    }
                } catch (Exception ignore) {}
            }
        };

        channelMetas.forEach(channelMeta -> {
            JedisChannel<?> channel = new JedisChannel<>(channelMeta.name(), UUID.randomUUID().toString(), TypeToken.of(channelMeta.type()), this.messengerPool, this.mapper);

            this.channels.put(channelMeta.type(), channel);
        });

        handlers.forEach(handler -> {
            JedisChannel channel = this.channels.get(handler.type());
            if (channel != null) {
                channel.addHandler(handler);
            }
        });

        executorService.submit(() -> listenerConnection.subscribe(pubSub, "centauri_redis"));
    }

    @Override
    public <T extends Message> Channel<T> getChannel(Class<T> type) {
        JedisChannel<T> channel = (JedisChannel<T>) this.channels.get(type);

        if (channel == null) {
            throw new IllegalArgumentException("The channel type " + type.getSimpleName() + " is not registered");
        }

        return channel;
    }
}
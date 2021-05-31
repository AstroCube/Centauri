package net.astrocube.commons.core.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.message.ChannelBinding;
import net.astrocube.api.core.message.Message;
import net.astrocube.api.core.message.MessageListener;
import net.astrocube.api.core.message.MessageMetadata;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class JedisChannel<T extends Message> implements Channel<T> {

	private final ChannelBinding<T> channelBinding;
	private final String id = UUID.randomUUID().toString();
	private final JedisPool jedisPool;
	private final ObjectMapper mapper;
	private final Set<MessageListener<T>> listeners;

	@Override
	public Channel<T> sendMessage(T object, Map<String, Object> headers)
		throws JsonProcessingException {

		MessageMetadata metadata = new MessageMetadata(
			headers,
			UUID.randomUUID().toString(),
			channelBinding.getName(),
			id,
			LocalDateTime.now()
		);

		ObjectNode message = mapper.createObjectNode();

		message.putPOJO("metadata", metadata);
		message.putPOJO("message", object);

		try (Jedis jedis = jedisPool.getResource()) {
			jedis.publish(JedisMessenger.CHANNEL_NAME, mapper.writeValueAsString(message));
		}
		return this;
	}

	@Override
	public String getName() {
		return channelBinding.getName();
	}

	@Override
	public Class<T> getType() {
		return channelBinding.getType();
	}

	public void callListeners(T object, MessageMetadata metadata) {
		for (MessageListener<T> listener : listeners) {
			listener.handleDelivery(object, metadata);
		}
	}
}
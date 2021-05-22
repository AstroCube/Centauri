package net.astrocube.commons.core.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import net.astrocube.api.core.message.Message;
import net.astrocube.api.core.message.MessageMetadata;
import redis.clients.jedis.JedisPubSub;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class JedisSubscriber extends JedisPubSub {

	private final ObjectMapper mapper;
	private final Map<String, JedisChannel<?>> channelsByName;

	/**
	 * Handles a redis message. The {@code channelName} is
	 * commonly {@link JedisMessenger#CHANNEL_NAME} because
	 * it's the unique channel we listen.
	 *
	 * This method expects that the given {@code message} is
	 * a JSON-string similar to this:
	 * <code>
	 * {
	 * 	 "metadata": {
	 * 	   "headers": {...},
	 * 	   "messageId": "...",
	 * 	   "channelName": "...",
	 * 	   "senderId": "...",
	 * 	   "timestamp": ...
	 * 	 },
	 * 	 "message": {...}
	 * }
	 * </code>
	 *
	 * Where the "metadata" is (commonly) only handled by this method,
	 * and the "message" is passed to every registered message listener
	 * for the channel specified in "metadata.channelName"
	 */
	@Override
	public void onMessage(String channelName, String message) {
		try {
			JsonNode jsonMessage = mapper.readTree(message);
			JsonNode jsonMetadata = jsonMessage.get("metadata");

			if (jsonMetadata == null) {
				return;
			}

			MessageMetadata metadata = mapper.treeToValue(jsonMetadata, MessageMetadata.class);
			JedisChannel<?> jedisChannel = channelsByName.get(metadata.getChannelName());

			// if the channel exists and we are not the senders, then call listeners
			if (jedisChannel != null && !metadata.getSenderId().equals(jedisChannel.getId())) {
				callListeners(metadata, jedisChannel, jsonMessage.get("message"));
			}
		} catch (Exception e) {
			Logger.getGlobal().log(
				Level.WARNING,
				"Error while reading redis message",
				e
			);
		}
	}

	/**
	 * Type-safe method responsible of parsing the
	 * given {@code jsonMessage} to the required
	 * {@code T} type. Then it calls all the
	 * {@code channel} listeners.
	 * @throws JsonProcessingException If an error
	 * occurs while converting the json message to
	 * a java object
	 */
	private <T extends Message> void callListeners(
		MessageMetadata metadata,
	  JedisChannel<T> channel,
	 	JsonNode jsonMessage
	) throws JsonProcessingException {
		T message = mapper.treeToValue(
			jsonMessage,
			channel.getType()
		);
		channel.callListeners(message, metadata);
	}

}

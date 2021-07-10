package net.astrocube.api.core.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.astrocube.api.core.model.Document;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Represents the metadata of a message,
 * contains information like the message
 * sender app id, the timestamp, etc.
 */
public class MessageMetadata implements Document {

	private final Map<String, Object> headers;
	private final String messageId;
	private final String channelName;
	private final String senderId;
	private final LocalDateTime timestamp;

	@ConstructorProperties({"headers", "messageId", "appId", "instanceId", "timestamp"})
	public MessageMetadata(
		Map<String, Object> headers,
	 	String messageId,
	 	String channelName,
	 	String senderId,
	 	LocalDateTime timestamp
	) {
		this.headers = headers;
		this.messageId = messageId;
		this.channelName = channelName;
		this.senderId = senderId;
		this.timestamp = timestamp;
	}

	/** Returns the message headers */
	public Map<String, Object> getHeaders() {
		return headers;
	}

	/** Returns the message identifier */
	public String getMessageId() {
		return messageId;
	}

	/** Returns the channel name */
	@JsonProperty("appId")
	public String getChannelName() {
		return channelName;
	}

	/** Returns the instance identifier */
	@JsonProperty("instanceId")
	public String getSenderId() {
		return senderId;
	}

	/** Returns the message timestamp */
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

}

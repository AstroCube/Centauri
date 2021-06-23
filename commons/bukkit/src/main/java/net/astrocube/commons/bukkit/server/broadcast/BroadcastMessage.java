package net.astrocube.commons.bukkit.server.broadcast;

import net.astrocube.api.core.message.Message;

import java.beans.ConstructorProperties;

public class BroadcastMessage implements Message {

	private final String message;

	@ConstructorProperties({"message"})
	public BroadcastMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}

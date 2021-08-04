package net.astrocube.commons.bukkit.party.channel.message;

import net.astrocube.api.core.message.Message;

import java.beans.ConstructorProperties;

public class PartyMessage implements Message {

	private final String senderMessage;
	private final String message;

	@ConstructorProperties({
		"senderMessage",
		"message"
	})
	public PartyMessage(String senderMessage, String message) {
		this.senderMessage = senderMessage;
		this.message = message;
	}

	public String getSenderMessage() {
		return senderMessage;
	}

	public String getMessage() {
		return message;
	}

}

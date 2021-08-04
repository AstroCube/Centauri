package net.astrocube.commons.bukkit.party.channel.message;

import net.astrocube.api.core.message.Message;

import java.beans.ConstructorProperties;

public class PartyMessage implements Message {

	private final String senderMessage;
	private final String message;
	private final String partyId;

	@ConstructorProperties({
		"senderMessage",
		"message",
		"partyId"
	})
	public PartyMessage(String senderMessage, String message, String partyId) {
		this.senderMessage = senderMessage;
		this.message = message;
		this.partyId = partyId;
	}

	public String getSenderMessage() {
		return senderMessage;
	}

	public String getMessage() {
		return message;
	}

	public String getPartyId() {
		return partyId;
	}

}

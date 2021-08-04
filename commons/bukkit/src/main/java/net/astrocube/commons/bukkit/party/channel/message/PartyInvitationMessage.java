package net.astrocube.commons.bukkit.party.channel.message;

import net.astrocube.api.core.message.Message;

import java.beans.ConstructorProperties;

public class PartyInvitationMessage implements Message {

	private final String playerNameInviter;
	private final String playerNameInvited;
	
	@ConstructorProperties(
		{"playerNameInviter",
		"playerNameInvited"}
	)
	public PartyInvitationMessage(String playerNameInviter, String playerNameInvited) {
		this.playerNameInviter = playerNameInviter;
		this.playerNameInvited = playerNameInvited;
	}

	public String getPlayerNameInviter() {
		return playerNameInviter;
	}

	public String getPlayNameInvited() {
		return playerNameInvited;
	}

}

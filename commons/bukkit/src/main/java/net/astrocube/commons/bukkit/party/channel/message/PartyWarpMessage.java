package net.astrocube.commons.bukkit.party.channel.message;

import net.astrocube.api.core.message.Message;

import java.util.Set;

public class PartyWarpMessage implements Message {

	private final String partyId;
	private final Set<String> users;

	public PartyWarpMessage(String partyId, Set<String> users) {
		this.partyId = partyId;
		this.users = users;
	}

	public String getPartyId() {
		return partyId;
	}

	public Set<String> getUsers() {
		return users;
	}

}

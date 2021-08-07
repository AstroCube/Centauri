package net.astrocube.commons.bukkit.party.channel.message;

import net.astrocube.api.core.message.Message;

import java.beans.ConstructorProperties;
import java.util.Set;

public class PartyWarpMessage implements Message {

	private final String partyId;
	private final String serverName;
	private final Set<String> users;

	@ConstructorProperties({"partyId", "users", "serverName"})
	public PartyWarpMessage(String partyId, Set<String> users, String serverName) {
		this.partyId = partyId;
		this.users = users;
		this.serverName = serverName;
	}

	public String getPartyId() {
		return partyId;
	}

	public Set<String> getUsers() {
		return users;
	}

	public String getServerName() {
		return serverName;
	}

}

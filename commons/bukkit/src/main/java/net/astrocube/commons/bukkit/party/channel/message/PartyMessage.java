package net.astrocube.commons.bukkit.party.channel.message;

import net.astrocube.api.core.message.Message;

import java.beans.ConstructorProperties;

public class PartyMessage implements Message {

	private final String path;
	private final String partyId;
	private final String[] replacements;

	@ConstructorProperties(
		{
			"path",
			"partyId",
			"replacements"
		}
	)
	public PartyMessage(String path, String partyId, String[] replacements) {
		this.path = path;
		this.partyId = partyId;
		this.replacements = replacements;
	}

	public String getPath() {
		return path;
	}

	public String getPartyId() {
		return partyId;
	}

	public String[] getReplacements() {
		return replacements;
	}

}

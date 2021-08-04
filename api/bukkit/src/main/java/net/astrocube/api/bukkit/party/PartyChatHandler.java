package net.astrocube.api.bukkit.party;

import net.astrocube.api.core.virtual.party.Party;
import org.bukkit.entity.Player;

/**
 * This class handle the chat in party
 */

public interface PartyChatHandler {

	/**
	 * Send the message all servers
	 * @param player the player that sent message
	 * @param message the message
	 */

	void chatParty(Party party, Player player, String message);

	/**
	 *
	 * @param partyId the party id
	 * @params sender the player that sent message
	 * @param message the message
	 */

	void chatServer(String partyId, String sender, String message);

}

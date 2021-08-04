package net.astrocube.api.bukkit.party;

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

	void chatGlobal(Player player, String message);

	/**
	 * Send the message to server
	 * @params sender the player that sent message
	 * @param message the message
	 */

	void chatServer(String sender, String message);

}

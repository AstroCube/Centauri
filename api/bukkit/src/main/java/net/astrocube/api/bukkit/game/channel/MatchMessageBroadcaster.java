package net.astrocube.api.bukkit.game.channel;

import org.bukkit.entity.Player;

public interface MatchMessageBroadcaster {

	/**
	 * Broadcast a match message
	 * @param message to broadcast
	 * @param player  who broadcast
	 * @throws Exception
	 */
	void sendMessage(String message, Player player) throws Exception;

	/**
	 * Broadcast a message with shoutout option
	 * @param message to broadcast
	 * @param player  to broadcast
	 * @param shout   if enabled
	 * @param all     if every involved can read
	 * @throws Exception
	 */
	void sendMessage(String message, Player player, boolean shout, boolean all) throws Exception;

}

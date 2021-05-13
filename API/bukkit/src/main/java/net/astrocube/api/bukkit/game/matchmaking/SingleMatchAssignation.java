package net.astrocube.api.bukkit.game.matchmaking;

import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.message.Message;

/**
 * Key - Value simple match for {@link Channel}
 * messaging.
 */
public interface SingleMatchAssignation extends Message {

	/**
	 * @return user of the assignation.
	 */
	String getUser();

	/**
	 * @return match of the assignation.
	 */
	String getMatch();

	/**
	 * @return server
	 */
	String getServer();

}

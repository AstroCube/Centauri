package net.astrocube.api.bukkit.game.matchmaking;

import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.message.Message;

import java.beans.ConstructorProperties;

/**
 * Key - Value simple match for {@link Channel}
 * messaging.
 */
public class SingleMatchAssignation implements Message {

	private final String user;
	private final String match;
	private final String server;

	@ConstructorProperties({"user", "match", "server"})
	public SingleMatchAssignation(String user, String match, String server) {
		this.user = user;
		this.match = match;
		this.server = server;
	}

	/**
	 * @return user of the assignation.
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @return match of the assignation.
	 */
	public String getMatch() {
		return match;
	}

	/**
	 * @return server
	 */
	public String getServer() {
		return server;
	}

}

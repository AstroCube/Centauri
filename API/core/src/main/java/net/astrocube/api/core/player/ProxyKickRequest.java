package net.astrocube.api.core.player;

import net.astrocube.api.core.message.Message;

/**
 * Represents a {@link Message} commonly sent
 * from Bukkit servers and handled by proxies.
 *
 * <p>When this message is sent, all proxies
 * try to kick the player with the given
 * {@code name}</p>
 */
public class ProxyKickRequest implements Message {

	private final String name;
	private final String reason;

	public ProxyKickRequest(String name, String reason) {
		this.name = name;
		this.reason = reason;
	}

	/** Returns the name of the player to kick */
	public String getName() {
		return name;
	}

	/** Returns the reason of the kick */
	public String getReason() {
		return reason;
	}

}

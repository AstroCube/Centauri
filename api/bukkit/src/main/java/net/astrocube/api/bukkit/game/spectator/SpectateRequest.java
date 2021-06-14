package net.astrocube.api.bukkit.game.spectator;

import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.message.Message;

public class SpectateRequest implements Message {

	private final String requester;
	private final Match match;
	private final String server;

	public SpectateRequest(
			String requester,
			Match match,
			String server
	) {
		this.requester = requester;
		this.match = match;
		this.server = server;
	}

	/**
	 * @return requester id
	 */
	public String getRequester() {
		return requester;
	}

	/**
	 * @return match id
	 */
	public Match getMatch() {
		return match;
	}

	/**
	 * @return server id
	 */
	public String getServer() {
		return server;
	}

	public enum State {
		SUCCESS, ERROR, VOIDED
	}

}

package net.astrocube.api.core.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.astrocube.api.core.message.Message;

import java.beans.ConstructorProperties;

public class ServerAliveMessage implements Message {

	private final String server;
	private final Action action;

	@ConstructorProperties({"server", "action"})
	public ServerAliveMessage(String server, Action action) {
		this.server = server;
		this.action = action;
	}

	/** Returns the server that will be requested/used. */
	public String getServer() {
		return server;
	}

	/** Returns the action to be broadcasted / received. */
	public Action getAction() {
		return action;
	}

	public enum Action {
		@JsonProperty("Request")
		REQUEST,
		@JsonProperty("Confirm")
		CONFIRM
	}

}

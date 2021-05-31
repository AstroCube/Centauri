package net.astrocube.api.core.session;

import net.astrocube.api.core.message.Message;
import net.astrocube.api.core.virtual.user.User;

import java.beans.ConstructorProperties;

public class SessionSwitchWrapper implements Message {

	private final User user;
	private final boolean connecting;

	@ConstructorProperties({"user", "connecting"})
	public SessionSwitchWrapper(User user, boolean connecting) {
		this.user = user;
		this.connecting = connecting;
	}

	/**
	 * @return user who is switching status
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @return if is connecting or disconnecting
	 */
	public boolean isConnecting() {
		return connecting;
	}

}

package net.astrocube.api.core.session;

import net.astrocube.api.core.message.Message;
import net.astrocube.api.core.virtual.user.User;

public interface SessionSwitchWrapper extends Message {

	/**
	 * @return user who is switching status
	 */
	User getUser();

	/**
	 * @return if is connecting or disconnecting
	 */
	boolean isConnecting();

}

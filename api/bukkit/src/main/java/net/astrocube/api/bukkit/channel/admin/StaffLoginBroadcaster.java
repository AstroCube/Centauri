package net.astrocube.api.bukkit.channel.admin;

import net.astrocube.api.core.virtual.user.User;

public interface StaffLoginBroadcaster {

	/**
	 * Performs a session switch broadcast for any staff
	 * @param session    to broadcast
	 * @param important  if is important
	 * @param connecting if is connecting or disconnecting
	 */
	void broadcastLogin(User session, boolean important, boolean connecting);

}
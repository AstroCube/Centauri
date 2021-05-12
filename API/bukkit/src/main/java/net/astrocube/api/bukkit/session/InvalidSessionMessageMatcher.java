package net.astrocube.api.bukkit.session;

import net.astrocube.api.core.virtual.user.User;

public interface InvalidSessionMessageMatcher {

	/**
	 * Will generate message indicating multi-account when authentication not authorized by the backend
	 */
	String generateSessionMessage(User user);
}

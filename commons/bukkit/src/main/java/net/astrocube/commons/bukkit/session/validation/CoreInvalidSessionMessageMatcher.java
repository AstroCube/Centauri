package net.astrocube.commons.bukkit.session.validation;

import net.astrocube.api.bukkit.session.InvalidSessionMessageMatcher;
import net.astrocube.api.core.virtual.user.User;

public class CoreInvalidSessionMessageMatcher implements InvalidSessionMessageMatcher {

	@Override
	public String generateSessionMessage(User user) {
		return "";
	}
}

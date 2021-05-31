package net.astrocube.api.bukkit.authentication;

import net.astrocube.api.core.virtual.user.User;

public interface AuthenticationGateway {

	void startProcessing(User user);

	String getName();

}

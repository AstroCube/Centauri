package net.astrocube.api.bukkit.authentication.gateway;

import net.astrocube.api.bukkit.authentication.UserAuthorization;

public interface AuthenticationService {

	/**
	 * Perform register action at backend
	 * @param authorization data to be attempted
	 */
	void register(UserAuthorization authorization) throws Exception;

	/**
	 * Perform default login action at backend
	 * @param authorization data to be attempted
	 */
	void login(UserAuthorization authorization) throws Exception;

}

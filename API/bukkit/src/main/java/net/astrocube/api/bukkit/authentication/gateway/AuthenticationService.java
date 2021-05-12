package net.astrocube.api.bukkit.authentication.gateway;

import net.astrocube.api.bukkit.authentication.BasicAuthorization;

public interface AuthenticationService {

	/**
	 * Perform register action at backend
	 * @param authorization data to be attempted
	 */
	void register(BasicAuthorization authorization) throws Exception;

	/**
	 * Perform default login action at backend
	 * @param authorization data to be attempted
	 */
	void login(BasicAuthorization authorization) throws Exception;

}

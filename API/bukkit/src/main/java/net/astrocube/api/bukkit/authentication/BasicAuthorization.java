package net.astrocube.api.bukkit.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface BasicAuthorization {

	/**
	 * Id of user to be authorized
	 * @return user Id
	 */
	@JsonProperty("user")
	String getId();

	/**
	 * Password to be matched at authorization
	 * @return user Id
	 */
	String getPassword();

	/**
	 * Address to be recorded
	 * @return address Id
	 */
	String getAddress();

}

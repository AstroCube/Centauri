package net.astrocube.api.bukkit.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bukkit.entity.Player;

import java.beans.ConstructorProperties;

public class BasicAuthorization {

	private final String id;
	private final String password;
	private final String address;

	@ConstructorProperties({"id", "password", "address"})
	public BasicAuthorization(
			String id,
			String password,
			String address
	) {
		this.id = id;
		this.password = password;
		this.address = address;
	}

	/**
	 * Id of user to be authorized
	 * @return user Id
	 */
	@JsonProperty("user")
	public String getId() {
		return id;
	}

	/**
	 * Password to be matched at authorization
	 * @return user Id
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Address to be recorded
	 * @return address Id
	 */
	public String getAddress() {
		return address;
	}

	public static BasicAuthorization withPassword(Player player, String password) {
		return new BasicAuthorization(
				player.getDatabaseIdentifier(),
				password,
				player.getAddress().getAddress().toString().replace("/", "")
		);
	}

}

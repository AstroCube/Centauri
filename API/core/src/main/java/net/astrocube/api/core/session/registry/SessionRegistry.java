package net.astrocube.api.core.session.registry;

import net.astrocube.api.core.model.Document;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;

public class SessionRegistry implements Document {

	private final String user;
	private final String version;
	private LocalDateTime authorizationDate;
	private String authorization;
	private boolean pending;
	private final String address;

	@ConstructorProperties({"user", "version", "authorizationDate",
			"authorization", "pending", "address"})
	public SessionRegistry(
			String user,
			String version,
			LocalDateTime authorizationDate,
			String authorization,
			boolean pending,
			String address
	) {
		this.user = user;
		this.version = version;
		this.authorizationDate = authorizationDate;
		this.authorization = authorization;
		this.pending = pending;
		this.address = address;
	}

	/**
	 * User who authorized before the game session
	 * @return user linked to session
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Version that user logged in
	 * @return minecraft version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Date of authorization
	 * @return dateTime
	 */
	public LocalDateTime getAuthorizationDate() {
		return authorizationDate;
	}

	public void setAuthorizationDate(LocalDateTime authorizationDate) {
		this.authorizationDate = authorizationDate;
	}

	/**
	 * Return identifier of authorization method used by the {@link net.astrocube.api.core.virtual.user.User}
	 * @return authorization indicator
	 */
	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

	/**
	 * Marked as pending only when the user has not been allowed to access the game
	 * @return pending indicator
	 */
	public boolean isPending() {
		return pending;
	}

	public void setPending(boolean pending) {
		this.pending = pending;
	}

	/**
	 * Address which was registered at authentication to generate post-validations.
	 * @return IP address
	 */
	public String getAddress() {
		return address;
	}

}

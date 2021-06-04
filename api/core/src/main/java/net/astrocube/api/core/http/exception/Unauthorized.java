package net.astrocube.api.core.http.exception;

/**
 * An API request was not allowed
 */
public class Unauthorized extends BackendException {

	public Unauthorized(String message) {
		super(message);
	}

	@Override
	public int statusCode() {
		return 403;
	}
}
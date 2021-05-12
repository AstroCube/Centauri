package net.astrocube.api.core.http.exception;

/**
 * Usually thrown when the server fails doing the requested task
 */
public class InternalServerError extends BackendException {

	public InternalServerError(String message) {
		super(message);
	}

	@Override
	public int statusCode() {
		return 500;
	}
}
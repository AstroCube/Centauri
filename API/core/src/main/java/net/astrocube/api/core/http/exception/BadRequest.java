package net.astrocube.api.core.http.exception;

/**
 * Usually thrown when the request was not given as expected
 */
public class BadRequest extends BackendException {

    public BadRequest(String message) {
        super(message);
    }

    @Override
    public int statusCode() {
        return 400;
    }
}
package net.astrocube.api.core.http.exception;

/**
 * Something was referenced through the API that does not exist
 */
public class NotFound extends BackendException {

    public NotFound(String message) {
        super(message);
    }

    @Override
    public int statusCode() {
        return 404;
    }
}
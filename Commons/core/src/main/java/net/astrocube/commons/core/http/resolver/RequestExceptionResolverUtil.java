package net.astrocube.commons.core.http.resolver;

import net.astrocube.api.core.http.exception.*;

public class RequestExceptionResolverUtil {

    public static BackendException generateException(String json, int statusCode) {
        switch (statusCode) {
            case 404:
                return new NotFound(json);
            case 403:
                return new Unauthorized(json);
            case 400:
                return new BadRequest(json);
        }
        return new InternalServerError(json);
    }
}

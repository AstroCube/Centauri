package net.astrocube.api.core.http.resolver;

import com.google.api.client.http.HttpIOExceptionHandler;

public interface RequestExceptionHandler {

    /**
     * Will return a backoff exception handler for the HTTP Client
     * @return exception handler
     */
    HttpIOExceptionHandler getExceptionBackoff();

}

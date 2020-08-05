package net.astrocube.api.core.http;

import com.google.api.client.http.HttpRequest;

public interface RequestCallable<T> {

    /**
     * Generate callable request to be correctly handled and parsed
     * @param request to be called
     * @return successfully parsed object
     * @throws Exception when not serialized correctly or thrown a request error
     */
    T call(HttpRequest request) throws Exception;

}
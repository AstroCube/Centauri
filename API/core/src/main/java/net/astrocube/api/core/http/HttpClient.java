package net.astrocube.api.core.http;

import com.google.inject.Singleton;
import net.astrocube.api.core.concurrent.AsyncResponse;

@Singleton
public interface HttpClient {

    /**
     * Will execute request to the desired address in order to receive a successful response
     * @param path to append after configured address
     * @param returnType to be expected
     * @param options of the request
     * @param <T> to be handled during all request
     * @return serialized object from provided URL and request options
     */
    <T> T executeRequestSync(String path, Class<T> returnType, RequestOptions options) throws Exception;

    /**
     * Will execute request to the desired address in order to receive a successful response
     * @param path to append after configured address
     * @param returnType to be expected
     * @param options of the request
     * @param <T> to be handled during all request
     * @return async response with serialized object from provided URL and request options
     */
    <T> AsyncResponse<T> executeRequest(String path, Class<T> returnType, RequestOptions options) throws Exception;

}

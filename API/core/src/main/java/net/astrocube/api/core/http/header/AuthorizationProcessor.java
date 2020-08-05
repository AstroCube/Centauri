package net.astrocube.api.core.http.header;

import com.google.inject.Singleton;

@Singleton
public interface AuthorizationProcessor {

    /**
     * Authorization header to be provided at most of backend requests.
     */
    char[] getAuthorizationToken();

    /**
     * Generate token authorization. Should be used only once, otherwise will fail
     * @param token to be stored as Authorization
     * @throws Exception when backend is authorized twice
     */
    void authorizeBackend(char[] token) throws Exception;

}

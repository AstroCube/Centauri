package net.astrocube.api.core.http.config;

import com.google.inject.Singleton;

@Singleton
public interface HttpClientConfig {

    /**
     * Return base url of the API
     * @return base URL
     */
    String getBaseURL();

}

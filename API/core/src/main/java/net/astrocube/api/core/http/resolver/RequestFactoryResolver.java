package net.astrocube.api.core.http.resolver;

import com.google.api.client.http.HttpRequestFactory;

public interface RequestFactoryResolver {

    /**
     * Will resolve configuration binding to create request factory
     * @return factory
     */
    HttpRequestFactory configureFactory();

}

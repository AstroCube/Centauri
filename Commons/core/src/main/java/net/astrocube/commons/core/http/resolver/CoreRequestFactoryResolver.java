package net.astrocube.commons.core.http.resolver;

import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.inject.Inject;
import net.astrocube.api.core.http.resolver.RequestExceptionHandler;
import net.astrocube.api.core.http.resolver.RequestFactoryResolver;
import net.astrocube.api.core.http.config.HttpFactoryConfig;

public class CoreRequestFactoryResolver implements RequestFactoryResolver {

    private @Inject HttpFactoryConfig httpFactoryConfig;
    private @Inject RequestExceptionHandler requestExceptionHandler;

    public HttpRequestFactory configureFactory() {
        return new NetHttpTransport().createRequestFactory(request -> {
            request.setConnectTimeout(httpFactoryConfig.getConnectTimeout());
            request.setReadTimeout(httpFactoryConfig.getReadTimeout());
            request.setNumberOfRetries(httpFactoryConfig.getRetryNumber());
            request.setIOExceptionHandler(requestExceptionHandler.getExceptionBackoff());
        });
    }

}

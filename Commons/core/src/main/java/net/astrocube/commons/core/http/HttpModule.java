package net.astrocube.commons.core.http;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.http.HttpClient;
import net.astrocube.api.core.http.header.AuthorizationProcessor;
import net.astrocube.api.core.http.resolver.RequestExceptionHandler;
import net.astrocube.api.core.http.resolver.RequestExecutorResolver;
import net.astrocube.api.core.http.resolver.RequestFactoryResolver;
import net.astrocube.api.core.http.resolver.TransportLoggerModifier;
import net.astrocube.commons.core.http.header.CoreAuthorizationProcessor;
import net.astrocube.commons.core.http.resolver.CoreRequestExceptionHandler;
import net.astrocube.commons.core.http.resolver.CoreRequestExecutorResolver;
import net.astrocube.commons.core.http.resolver.CoreRequestFactoryResolver;
import net.astrocube.commons.core.http.resolver.CoreTransportLoggerModifier;

public class HttpModule extends ProtectedModule {

    @Override
    protected void configure() {
        bind(AuthorizationProcessor.class).to(CoreAuthorizationProcessor.class);
        bind(RequestExceptionHandler.class).to(CoreRequestExceptionHandler.class);
        bind(RequestExecutorResolver.class).to(CoreRequestExecutorResolver.class);
        bind(RequestFactoryResolver.class).to(CoreRequestFactoryResolver.class);
        bind(TransportLoggerModifier.class).to(CoreTransportLoggerModifier.class);
        bind(HttpClient.class).to(CoreHttpClient.class);
        expose(AuthorizationProcessor.class);
        expose(RequestExceptionHandler.class);
        expose(RequestExecutorResolver.class);
        expose(RequestFactoryResolver.class);
        expose(TransportLoggerModifier.class);
        expose(HttpClient.class);
    }

}

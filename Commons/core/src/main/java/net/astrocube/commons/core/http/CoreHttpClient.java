package net.astrocube.commons.core.http;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.inject.Inject;
import net.astrocube.api.core.http.HttpClient;
import net.astrocube.api.core.http.RequestCallable;
import net.astrocube.api.core.http.RequestOptions;
import net.astrocube.api.core.http.config.HttpClientConfig;
import net.astrocube.api.core.http.header.AuthorizationProcessor;
import net.astrocube.api.core.http.resolver.RequestFactoryResolver;
import net.astrocube.api.core.http.resolver.TransportLoggerModifier;
import net.astrocube.commons.core.http.request.RequestContentBuilderUtil;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CoreHttpClient implements HttpClient {

    private final Logger logger;
    private final HttpClientConfig httpClientConfig;
    private final HttpRequestFactory requestFactory;
    private final AuthorizationProcessor authorizationProcessor;

    @Inject
    private CoreHttpClient(
        RequestFactoryResolver factoryResolver,
        TransportLoggerModifier transportLoggerModifier,
        HttpClientConfig httpClientConfig,
        AuthorizationProcessor authorizationProcessor
    ) {
        this.logger = Logger.getLogger(getClass().getName());
        this.requestFactory = factoryResolver.configureFactory();
        this.httpClientConfig = httpClientConfig;
        this.authorizationProcessor = authorizationProcessor;
        transportLoggerModifier.overrideDefaultLogger(this.logger);
    }


    @Override
    public <T> T executeRequestSync(String path, RequestCallable<T> returnType, RequestOptions options) throws Exception {
        try {
            HttpRequest request = RequestContentBuilderUtil.build(
                    requestFactory,
                    options,
                    httpClientConfig.getBaseURL(),
                    path
            );

            options.getHeaders().forEach((key, value) -> request.getHeaders().set(key, value));
            request.getHeaders().setAccept("application/json");
            request.getHeaders().set("Authorization", "Bearer " + new String(authorizationProcessor.getAuthorizationToken()));
            return returnType.call(request);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to build request to " + path, e);
            throw e;
        }
    }

    @Override
    public <T> T executeHeadlessRequestSync(String path, RequestCallable<T> returnType, RequestOptions options) throws Exception {
        try {
            HttpRequest request = RequestContentBuilderUtil.build(
                    requestFactory,
                    options,
                    path,
                    ""
            );

            options.getHeaders().forEach((key, value) -> request.getHeaders().set(key, value));
            request.getHeaders().setAccept("application/json");
            request.getHeaders().set("Authorization", "Bearer " + new String(authorizationProcessor.getAuthorizationToken()));
            return returnType.call(request);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to build request to " + path, e);
            throw e;
        }
    }

}

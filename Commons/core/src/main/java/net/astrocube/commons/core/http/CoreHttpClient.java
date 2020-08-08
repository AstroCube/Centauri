package net.astrocube.commons.core.http;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.inject.Inject;
import net.astrocube.api.core.concurrent.AsyncResponse;
import net.astrocube.api.core.concurrent.Response;
import net.astrocube.api.core.concurrent.SimpleAsyncResponse;
import net.astrocube.api.core.concurrent.WrappedResponse;
import net.astrocube.api.core.http.HttpClient;
import net.astrocube.api.core.http.RequestCallable;
import net.astrocube.api.core.http.RequestOptions;
import net.astrocube.api.core.http.config.HttpClientConfig;
import net.astrocube.api.core.http.header.AuthorizationProcessor;
import net.astrocube.api.core.http.resolver.RequestExecutorResolver;
import net.astrocube.api.core.http.resolver.RequestFactoryResolver;
import net.astrocube.api.core.http.resolver.TransportLoggerModifier;
import net.astrocube.commons.core.http.request.RequestContentBuilderUtil;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CoreHttpClient implements HttpClient {

    private final Logger logger;
    private final ListeningExecutorService listeningExecutorService;
    private final HttpClientConfig httpClientConfig;
    private final HttpRequestFactory requestFactory;
    private final AuthorizationProcessor authorizationProcessor;

    @Inject
    private CoreHttpClient(
        RequestExecutorResolver executorResolver,
        RequestFactoryResolver factoryResolver,
        TransportLoggerModifier transportLoggerModifier,
        HttpClientConfig httpClientConfig,
        AuthorizationProcessor authorizationProcessor
    ) {
        this.logger = Logger.getLogger(getClass().getName());
        this.listeningExecutorService = executorResolver.getExecutor();
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
            request.getHeaders().setAuthenticate(Arrays.toString(authorizationProcessor.getAuthorizationToken()));
            return returnType.call(request);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to build request to " + path, e);
            throw e;
        }
    }

    @Override
    public <T> AsyncResponse<T> executeRequest(String path, RequestCallable<T> returnType, RequestOptions options) {
        return new SimpleAsyncResponse<>(this.listeningExecutorService.submit(() -> {
            try {
                return new WrappedResponse<>(Response.Status.SUCCESS, executeRequestSync(path, returnType, options), null);
            } catch (Exception exception) {
                return new WrappedResponse<>(Response.Status.ERROR, null, exception);
            }
        }), listeningExecutorService);
    }

}

package net.astrocube.commons.core.http;

import com.google.api.client.http.GenericUrl;
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
import net.astrocube.commons.core.http.request.RequestContent;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

public class CoreHttpClient implements HttpClient {

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
		Logger logger = Logger.getLogger(getClass().getName());
		this.requestFactory = factoryResolver.configureFactory();
		this.httpClientConfig = httpClientConfig;
		this.authorizationProcessor = authorizationProcessor;
		transportLoggerModifier.overrideDefaultLogger(logger);
	}


	@Override
	public <T> T executeRequestSync(String path, RequestCallable<T> returnType, RequestOptions options) throws Exception {
		HttpRequest request = buildRequest(
			options,
			httpClientConfig.getBaseURL(),
			path
		);

		options.getHeaders().forEach((key, value) -> request.getHeaders().set(key, value));
		request.getHeaders().setAccept("application/json");
		request.getHeaders().set("Authorization", "Bearer " + new String(authorizationProcessor.getAuthorizationToken()));
		return returnType.call(request);
	}

	@Override
	public <T> T executeHeadlessRequestSync(String path, RequestCallable<T> returnType, RequestOptions options) throws Exception {
		HttpRequest request = buildRequest(
			options,
			path,
			""
		);

		options.getHeaders().forEach((key, value) -> request.getHeaders().set(key, value));
		request.getHeaders().setAccept("application/json");
		request.getHeaders().set("Authorization", "Bearer " + new String(authorizationProcessor.getAuthorizationToken()));
		return returnType.call(request);
	}

	private HttpRequest buildRequest(RequestOptions options, String baseUrl, String path)
		throws IOException {
		return requestFactory.buildRequest(
			options.getType().name(),
			new GenericUrl(new URL(new URL(baseUrl), path + options.getQuery())),
			options.getBody() == null ? null : new RequestContent(options.getBody())
		);
	}

}

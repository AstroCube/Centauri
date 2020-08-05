package net.astrocube.commons.core.http.request;

import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import net.astrocube.api.core.http.RequestOptions;

import java.io.IOException;

public class RequestContentBuilderUtil {

    public static HttpRequest build(HttpRequestFactory factory, RequestOptions options, String baseURL, String path) throws IOException {
        return factory.buildRequest(
                options.getType().name(),
                UrlBuilderUtil.build(baseURL, path, options.getQuery()),
                buildContent(options)
        );
    }

    private static HttpContent buildContent(RequestOptions options) {
        return options.getBody() == null ? null : new RequestContent(options.getBody());
    }

}

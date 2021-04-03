package net.astrocube.commons.core.http;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import net.astrocube.api.core.http.RequestCallable;
import net.astrocube.commons.core.http.resolver.RequestExceptionResolverUtil;

public class RawRequestCallable implements RequestCallable<String> {

    @Override
    public String call(HttpRequest request) throws Exception {
        final HttpResponse response = request.execute();
        final String json = response.parseAsString();
        int statusCode = response.getStatusCode();

        if (statusCode == 200) {
            return json;
        } else {
            throw RequestExceptionResolverUtil.generateException(json, statusCode);
        }
    }


}

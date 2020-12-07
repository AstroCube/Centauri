package net.astrocube.commons.core.http;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.common.reflect.TypeToken;
import lombok.AllArgsConstructor;
import net.astrocube.api.core.http.RequestCallable;
import net.astrocube.commons.core.http.resolver.RequestExceptionResolverUtil;

@AllArgsConstructor
@SuppressWarnings("all")
public class CoreRequestCallable<T> implements RequestCallable<T> {

    private final TypeToken<T> returnType;
    private final ObjectMapper mapper;

    @Override
    public T call(HttpRequest request) throws Exception {
        final HttpResponse response = request.execute();
        final String json = response.parseAsString();
        int statusCode = response.getStatusCode();

        if (statusCode == 200) {

            JavaType type = mapper.constructType(returnType.getType());
            T returnable = this.mapper.readValue(json, type);
            System.out.println(returnType.getType().getTypeName());

            return returnable;
        } else {
            throw RequestExceptionResolverUtil.generateException(json, statusCode);
        }
    }

}
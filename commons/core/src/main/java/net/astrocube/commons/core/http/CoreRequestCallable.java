package net.astrocube.commons.core.http;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.common.reflect.TypeToken;
import lombok.AllArgsConstructor;
import net.astrocube.api.core.http.RequestCallable;
import net.astrocube.commons.core.http.resolver.RequestExceptionResolverUtil;

@SuppressWarnings("all")
@AllArgsConstructor
public class CoreRequestCallable<T> implements RequestCallable<T> {

	private final JavaType returnType;
	private final ObjectMapper mapper;

	public CoreRequestCallable(TypeToken<T> type, ObjectMapper mapper) {
		this.returnType = mapper.constructType(type.getType());
		this.mapper = mapper;
	}

	@Override
	public T call(HttpRequest request) throws Exception {
		final HttpResponse response = request.execute();
		final String json = response.parseAsString();
		int statusCode = response.getStatusCode();

		if (returnType.getRawClass() == Void.class) {
			return null;
		}

		if (statusCode == 200) {
			T returnable = this.mapper.readValue(json, returnType);
			return returnable;
		} else {
			throw RequestExceptionResolverUtil.generateException(json, statusCode);
		}
	}

}
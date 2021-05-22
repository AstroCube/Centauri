package net.astrocube.api.core.http;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class RequestOptions {

	private final Type type;
	private final Map<String, String> headers;
	private final String body;
	private final String query;

	public RequestOptions(
		Type type,
		@Nullable Map<String, String> headers,
		String body,
		@Nonnull String query
	) {
		this.type = type;
		this.headers = headers;
		this.body = body;
		this.query = query;
	}

	public RequestOptions(Type type, String body) {
		this.type = type;
		this.body = body;
		this.headers = new HashMap<>();
		this.query = "";
	}

	/**
	 * Will return type to be used at the request
	 * @return request type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Headers to append at request
	 * @return request headers
	 */
	public Map<String, String> getHeaders() {
		return headers;
	}

	/**
	 * Body to be used at the request
	 * @return body of the request
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Query params to append after type
	 * @return query type
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * Request posibilities
	 */
	public enum Type {
		GET, POST, DELETE, PUT
	}

}

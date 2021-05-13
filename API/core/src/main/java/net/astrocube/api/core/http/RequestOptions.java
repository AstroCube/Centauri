package net.astrocube.api.core.http;

import java.util.Map;

public interface RequestOptions {

	/**
	 * Will return type to be used at the request
	 * @return request type
	 */
	Type getType();

	/**
	 * Headers to append at request
	 * @return request headers
	 */
	Map<String, String> getHeaders();

	/**
	 * Body to be used at the request
	 * @return body of the request
	 */
	String getBody();

	/**
	 * Query params to append after type
	 * @return query type
	 */
	String getQuery();

	/**
	 * Request posibilities
	 */
	enum Type {
		GET, POST, DELETE, PUT
	}

}

package net.astrocube.api.core.redis;

public interface RedisConfiguration {

	/**
	 * Return string with configured redis server address
	 * @return string containing address
	 */
	String getAddress();

	/**
	 * Return port that will be used along the address
	 * @return integer with the port
	 */
	int getPort();

	/**
	 * Timeout before the connection can be established
	 * @return integer with the timeout
	 */
	int getTimeout();

	/**
	 * Number of the redis database to be used
	 * @return integer with the identifier
	 */
	int getDatabase();

	/**
	 * Return string containing the Redis connection password
	 * @return String with redis password
	 */
	String getPassword();

}
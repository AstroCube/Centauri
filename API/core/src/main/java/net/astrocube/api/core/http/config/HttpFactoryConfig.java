package net.astrocube.api.core.http.config;

public interface HttpFactoryConfig {

    /**
     * Timeout in milliseconds to establish a connection or 0 for an infinite
     * timeout.
     */
    int getConnectTimeout();

    /**
     * Timeout in milliseconds to read data from an established connection or 0
     * for an infinite timeout.
     */
    int getReadTimeout();

    /**
     * Number of retries to execute a request until giving up. 0 indicates no
     * retrying.
     */
    int getRetryNumber();

}

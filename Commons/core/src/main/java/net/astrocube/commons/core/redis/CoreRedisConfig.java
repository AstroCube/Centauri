package net.astrocube.commons.core.redis;

import net.astrocube.api.core.redis.RedisConfiguration;

public class CoreRedisConfig implements RedisConfiguration {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 6379;
    private static final int TIMEOUT = 2000;
    private static final int DATABASE = 0;
    private static final String PASSWORD = "";

    public String getAddress() {
        return ADDRESS;
    }

    public int getPort() {
        return PORT;
    }

    public int getDatabase() {
        return DATABASE;
    }

    public String getPassword() {
        return PASSWORD;
    }

    public int getTimeout() {
        return TIMEOUT;
    }
}
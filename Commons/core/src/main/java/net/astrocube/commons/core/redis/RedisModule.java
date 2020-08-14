package net.astrocube.commons.core.redis;

import net.astrocube.api.core.redis.Redis;
import net.astrocube.commons.core.inject.ProtectedModule;

public class RedisModule extends ProtectedModule {

    @Override
    protected void configure() {
        bind(Redis.class).to(CoreRedis.class);
        expose(Redis.class);
    }
}
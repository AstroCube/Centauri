package net.astrocube.commons.core.redis;

import net.astrocube.inject.ProtectedModule;
import net.astrocube.api.core.redis.Redis;

public class RedisModule extends ProtectedModule {

	@Override
	protected void configure() {
		bind(Redis.class).to(CoreRedis.class);
		expose(Redis.class);
	}
}
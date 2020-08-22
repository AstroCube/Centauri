package net.astrocube.commons.bukkit.authentication.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.authentication.server.AuthenticationCooldown;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.commons.bukkit.utils.TimeUtils;
import redis.clients.jedis.Jedis;

import java.util.Date;

@Singleton
public class CoreAuthenticationCooldown implements AuthenticationCooldown {

    private final Jedis jedis;

    @Inject
    public CoreAuthenticationCooldown(Redis redis) {
        this.jedis = redis.getRawConnection().getResource();
    }

    @Override
    public void setCooldownLock(String id) {
        jedis.set("authCooldown:" + id, TimeUtils.addMinutes(new Date(), 5).toInstant().toEpochMilli() + "");
        jedis.expire("authCooldown:" + id, 300);
    }

    @Override
    public boolean hasCooldown(String id) {
        return jedis.exists("authCooldown:" + id);
    }

    @Override
    public Date getRemainingTime(String id) {
        if (jedis.exists("authCooldown:" + id))
            return new Date(Long.parseLong(jedis.get("authCooldown:" + id)));
        return new Date();
    }

}

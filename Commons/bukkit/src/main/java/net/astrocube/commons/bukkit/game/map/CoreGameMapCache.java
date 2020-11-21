package net.astrocube.commons.bukkit.game.map;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.map.GameMapCache;
import net.astrocube.api.core.redis.Redis;
import redis.clients.jedis.Jedis;

import java.util.Base64;

@Singleton
public class CoreGameMapCache implements GameMapCache {

    private final Jedis redis;

    @Inject
    public CoreGameMapCache(Redis redis) {
        this.redis = redis.getRawConnection().getResource();
    }

    @Override
    public void registerFile(String id, byte[] file) {
        redis.set("mapFile:" + id, Base64.getEncoder().encodeToString(file));
        redis.expire("mapFile:" + id, 21600);
    }

    @Override
    public void registerConfiguration(String id, byte[] file) {
        redis.set("mapConfig:" + id, Base64.getEncoder().encodeToString(file));
        redis.expire("mapConfig:" + id, 21600);
    }

    @Override
    public byte[] getFile(String id) throws GameControlException {
        return getNeutral("mapFile:" + id);
    }

    @Override
    public byte[] getConfiguration(String id) throws GameControlException {
        return getNeutral("mapConfig:" + id);
    }

    @Override
    public boolean exists(String id) {
        return redis.exists("mapConfig:" + id) && redis.exists("mapFile:" + id);
    }

    private byte[] getNeutral(String match) throws GameControlException {
        if (!redis.exists(match)) {
            throw new GameControlException("Map is not cached");
        }

        return Base64.getDecoder().decode(redis.get(match));
    }
}

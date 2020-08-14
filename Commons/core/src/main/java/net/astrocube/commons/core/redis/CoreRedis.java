package net.astrocube.commons.core.redis;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.core.redis.Redis;
import org.bukkit.Bukkit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class CoreRedis implements Redis {

    private JedisPool jedisPool;
    private Jedis listenerConnection;

    @Inject
    CoreRedis(CoreRedisConfig coreRedisConfig) {
        JedisPoolConfig config = new JedisPoolConfig();
        try {
            listenerConnection = new Jedis(coreRedisConfig.getAddress(), coreRedisConfig.getPort(), coreRedisConfig.getTimeout());

            if (coreRedisConfig.getPassword() == null || coreRedisConfig.getPassword().isEmpty()) {
                this.jedisPool = new JedisPool(config, coreRedisConfig.getAddress(), coreRedisConfig.getPort(), coreRedisConfig.getTimeout());
            } else {
                this.jedisPool = new JedisPool(config, coreRedisConfig.getAddress(), coreRedisConfig.getPort(), coreRedisConfig.getTimeout());
                listenerConnection.auth(coreRedisConfig.getPassword());
            }
        } catch (JedisConnectionException e) {
            Logger.getLogger("redis").log(Level.SEVERE, "An exception occurred while initializing the needed jedis instances", e);
        }
    }

    @Override
    public JedisPool getRawConnection() {
        return this.jedisPool;
    }

    @Override
    public Jedis getListenerConnection() {
        return this.listenerConnection;
    }

    @Override
    public void close() throws IOException {
        getRawConnection().close();
        this.listenerConnection.close();
    }
}
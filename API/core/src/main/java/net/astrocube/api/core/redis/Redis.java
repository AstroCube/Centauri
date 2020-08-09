package net.astrocube.api.core.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.Closeable;

public interface Redis extends Closeable {

    /**
     * Will return raw redis Pool
     * @return Jedis Pool
     */
    JedisPool getRawConnection();

    /**
     * Will return raw redis connection
     * @return Listener pool
     */
    Jedis getListenerConnection();

}
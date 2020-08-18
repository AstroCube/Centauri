package net.astrocube.commons.bukkit.authentication.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.authentication.server.AuthenticationCooldown;
import net.astrocube.api.core.authentication.AuthorizeException;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.commons.bukkit.utils.TimeUtils;
import redis.clients.jedis.Jedis;

import java.util.Date;

@Singleton
public class CoreAuthenticationCooldown implements AuthenticationCooldown {

    private @Inject Redis redis;

    @Override
    public void setCooldownLock(String id) throws AuthorizeException {
        try (Jedis jedis = redis.getRawConnection().getResource()) {
            jedis.set("authCooldown:" + id, TimeUtils.addMinutes(new Date(), 5).getTime() + "");
            jedis.expire("authCooldown:" + id, 300);
        } catch (Exception exception) {
            throw new AuthorizeException("Error obtaining cooldown pool");
        }
    }

    @Override
    public boolean hasCooldown(String id) throws AuthorizeException {
        try (Jedis jedis = redis.getRawConnection().getResource()) {
            return jedis.exists("authCooldown:" + id);
        } catch (Exception exception) {
            throw new AuthorizeException("Error obtaining cooldown pool");
        }
    }

    @Override
    public Date getRemainingTime(String id) throws AuthorizeException {
        try (Jedis jedis = redis.getRawConnection().getResource()) {
            if (jedis.exists("authCooldown:" + id)) return new Date();
            return new Date();
        } catch (Exception exception) {
            throw new AuthorizeException("Error obtaining cooldown pool");
        }
    }

}

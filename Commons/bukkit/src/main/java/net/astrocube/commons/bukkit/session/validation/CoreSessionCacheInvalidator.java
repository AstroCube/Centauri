package net.astrocube.commons.bukkit.session.validation;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.session.SessionCacheInvalidator;
import net.astrocube.api.core.model.ModelMeta;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.api.core.virtual.user.UserDoc;
import redis.clients.jedis.Jedis;

@Singleton
public class CoreSessionCacheInvalidator implements SessionCacheInvalidator {

    private @Inject ModelMeta<User, UserDoc.Partial> modelMeta;
    private @Inject Redis redis;

    @Override
    public void invalidateSessionCache(User user) throws Exception {
        /**
         * In order to prevent errors when using {@link User} route we will
         * inject model meta instead of direct route typing.
         */
        try (Jedis jedis = redis.getRawConnection().getResource()) {
            jedis.del(modelMeta.getRouteKey() + ":" + user.getId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Could not invalidate user cache");
        }
    }

}

package net.astrocube.api.bukkit.session;

import net.astrocube.api.core.virtual.user.User;

public interface SessionCacheInvalidator {

    /**
     * Invalidate cache when user login/logout to prevent data glitching
     * @param user to be invalidated
     */
    void invalidateSessionCache(User user);

}

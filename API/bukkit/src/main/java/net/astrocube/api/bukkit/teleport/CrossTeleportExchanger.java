package net.astrocube.api.bukkit.teleport;

import net.astrocube.api.bukkit.teleport.request.TeleportRequest;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.virtual.user.User;

public interface CrossTeleportExchanger {

    /**
     * Schedule at {@link Redis} a small request to
     * determine at another server what type of procedure
     * must be done when the user reaches the server
     * @param request to be applied
     */
    void schedule(TeleportRequest request);

    /**
     * Retrieves essential data from the teleport request
     * in order to proceed with the cross-sever teleport.
     * @param user to teleport.
     */
    void exchange(User user);

}

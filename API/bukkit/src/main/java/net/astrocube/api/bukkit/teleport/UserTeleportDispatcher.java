package net.astrocube.api.bukkit.teleport;

import net.astrocube.api.core.virtual.user.User;

public interface UserTeleportDispatcher {

    /**
     * Perform a teleport request between two users
     * @param requester to teleport or serve as teleport point.
     * @param receiver to teleport or serve as teleport point.
     * @param self if requester will teleport or receive a teleport.
     */
    void teleport(User requester, User receiver, boolean self);

}

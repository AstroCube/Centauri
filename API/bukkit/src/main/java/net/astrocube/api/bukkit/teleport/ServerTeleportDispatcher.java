package net.astrocube.api.bukkit.teleport;

import net.astrocube.api.core.virtual.user.User;

import javax.annotation.Nullable;

public interface ServerTeleportDispatcher {

    /**
     * Perform a teleport request between two users
     * @param receiver to teleport at a certain server.
     * @param server where te user will teleport
     */
    void teleport(User receiver, String server);

    /**
     * Perform a teleport request between two users
     * @param receiver to teleport at a certain server.
     * @param requester if executed by other {@link User}
     * @param server where te user will teleport
     */
    void teleport(User receiver, @Nullable User requester, String server);

}

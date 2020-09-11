package net.astrocube.api.bukkit.teleport;

import net.astrocube.api.core.virtual.user.User;

public interface ServerTeleportDispatcher {

    /**
     * Perform a teleport request between two users
     * @param requester to teleport at a certain server.
     * @param server where te user will teleport
     */
    void teleport(User requester, String server);

}

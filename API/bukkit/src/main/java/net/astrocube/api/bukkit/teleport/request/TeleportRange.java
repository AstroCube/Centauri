package net.astrocube.api.bukkit.teleport.request;

public interface TeleportRange {

    /**
     * Indicates when {@link TeleportRequest} is allowed to be executed
     * when the requester is not sharing server with the receiver.
     * @return boolean indicating if cross-teleport is allowed
     */
    boolean isCrossAllowed();

    /**
     * Indicates when the request is cross-server.
     * @return boolean indicating cross-teleport allowance.
     */
    boolean isCrossServer();

}

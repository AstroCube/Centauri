package net.astrocube.api.bukkit.teleport.request;

import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Location;

import java.util.Optional;

public interface TeleportRequest {

    /**
     * A teleport range indicates server-related indicators like
     * server sharing and cross-server allowance.
     *
     * @return range object containing described properties
     */
    TeleportRange getRange();

    /**
     * Just in a certain case when the requester of the teleport
     * is a user, there will be a {@link User} linked to the
     * request.
     * @return user who requested the teleport
     */
    Optional<User> getRequester();

    /**
     * When the request was issued to an specific server
     * it will return the value of the wanted one.
     * @return server cloud name
     */
    Optional<String> getServer();

    /**
     * Get location where user will be teleport.
     *
     * NOTE: This only applies when you are trying to teleport
     * locally a user. When is cross-server it will just fail.
     *
     * @return server cloud name
     */
    Optional<Location> getLocation();

    /**
     * The user to teleport will be always present at the
     * request, so the corresponding handlers will know who
     * manipulate, even after transported to another server.
     * @return teleport receiver
     */
    User getReceiver();

}

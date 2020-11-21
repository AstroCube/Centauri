package net.astrocube.api.bukkit.teleport;

import net.astrocube.api.bukkit.teleport.request.TeleportRange;
import net.astrocube.api.core.virtual.user.User;

public interface RangeDiscriminator {

    /**
     * Discriminates the teleport range properties before executing
     * teleport.
     * @param requester to be held
     * @param receiver to be discriminated
     * @return teleport range to be used
     */
    TeleportRange discriminate(User requester, User receiver);

}

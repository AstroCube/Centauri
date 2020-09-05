package net.astrocube.api.bukkit.lobby.hide;

import net.astrocube.api.core.virtual.user.User;

public interface HideJoinProcessor {

    /**
     * Apply compounds if hide active and applies already
     * connected user compounds.
     * @param user to be checked
     */
    void process(User user);

}

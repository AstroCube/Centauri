package net.astrocube.api.bukkit.lobby.hide;

import net.astrocube.api.core.virtual.user.User;

public interface HideCompoundMatcher {

    /**
     * Return selected compound for user
     * @param user where compound will be searched
     * @return user compound or default one
     */
    HideCompound getUserCompound(User user);

}

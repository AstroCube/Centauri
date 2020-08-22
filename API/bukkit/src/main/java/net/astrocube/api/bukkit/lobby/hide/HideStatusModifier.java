package net.astrocube.api.bukkit.lobby.hide;

import net.astrocube.api.core.virtual.user.User;

public interface HideStatusModifier {

    /**
     * Applies the selected {@link HideCompound} to the user
     * @param user to be affected
     * @param compound to be applied
     */
    void apply(User user, HideCompound compound);

    /**
     * Restore the user visibility to everyone
     * @param user to be restored
     */
    void restore(User user);

}

package net.astrocube.api.bukkit.lobby.hide;

import net.astrocube.api.core.virtual.user.User;
import org.bukkit.entity.Player;

public interface HideApplier {

    /**
     * Applies certain hide skip property to the player
     * @param user to be applied
     * @param player to be provided
     * @param target to be applied
     * @param targetPlayer to be provided
     */
    void apply(User user, Player player, User target, Player targetPlayer);

}

package net.astrocube.api.bukkit.game.match;

import net.astrocube.api.core.virtual.user.User;
import org.bukkit.entity.Player;

public interface UserMatchJoiner {

    /**
     * Determines the nature of the user's income,
     * either as a spectator or as a player.
     * @param user to be processed
     * @param player to be processed
     */
    void processJoin(User user, Player player) throws Exception;

    enum Status {
        WAITING, PLAYING, SPECTATING
    }

}

package net.astrocube.api.bukkit.game.lobby;

import net.astrocube.api.bukkit.virtual.game.match.Match;
import org.bukkit.entity.Player;

public interface LobbySessionManager {

    /**
     * Process certain lobby requirements before the player joins
     * to the match.
     * @param player to be processed
     * @param match where the player is located.
     */
    void connectUser(Player player, Match match);

    /**
     * Disconnect user from the match.
     * @param player to be disconnected
     */
    void disconnectUser(Player player);

}

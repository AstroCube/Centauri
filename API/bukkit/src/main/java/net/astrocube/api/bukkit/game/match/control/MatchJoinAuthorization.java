package net.astrocube.api.bukkit.game.match.control;

import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.match.MatchAssigner;
import net.astrocube.api.bukkit.virtual.game.match.Match;

import java.util.Optional;

public interface MatchJoinAuthorization {

    /**
     * Generate a temporal authorization in order to keep in track which
     * match was given to the user by the {@link MatchAssigner}.
     * @param match to be paired
     * @param user to be authorized
     */
    void generateMatchAuthorization(String match, String user);

    /**
     *
     * @param user to be paired
     * @return match that has been authorized.
     */
    Match processAuthorization(String user) throws Exception;

}

package net.astrocube.api.bukkit.game.match.control;

import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.virtual.user.User;

import java.util.Set;

public interface MatchParticipantsProvider {

    /**
     * Get all the match pending users
     * @param match to be accessed
     * @return provided users
     */
    Set<User> getMatchPending(Match match);

    /**
     * Get all the match spectators.
     * @param match to be accessed
     * @return provided users
     */
    Set<User> getMatchSpectators(Match match);

    /**
     * Get all the match active players.
     * @param match to be accessed
     * @return provided users
     */
    Set<User> getMatchUsers(Match match);

}

package net.astrocube.api.bukkit.game.match;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.virtual.user.User;

/**
 * Assign certain group of {@link User} to a {@link Match}.
 */
public interface MatchAssigner {

    /**
     * Create a record to be assigned by another server on
     * player join
     * @param assignable users to the match
     * @param match to be assigned
     */
    void assign(MatchAssignable assignable, Match match) throws Exception;

}

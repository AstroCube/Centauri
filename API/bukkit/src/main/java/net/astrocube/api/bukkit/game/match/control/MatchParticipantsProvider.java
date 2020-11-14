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

}

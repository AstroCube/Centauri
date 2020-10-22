package net.astrocube.api.bukkit.game.matchmaking;

import net.astrocube.api.bukkit.virtual.game.match.Match;

import java.util.Set;

/**
 * Filter available match to be paired with the
 * actual matchmaking request.
 */
public interface AvailableMatchProvider {

    /**
     * Obtain matches that meet the matchmaking request
     * criteria.
     * @param request where criteria will be extracted.
     * @return set of available matches.
     */
    Set<Match> getCriteriaAvailableMatches(MatchmakingRequest request) throws Exception;

}

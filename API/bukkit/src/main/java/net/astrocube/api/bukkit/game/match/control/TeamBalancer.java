package net.astrocube.api.bukkit.game.match.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;

import java.util.Set;

public interface TeamBalancer {

    /**
     * Obtain balanced {@link MatchDoc.Team} in order
     * to get equitable groups for each assignation.
     * @param assignations to be sorted
     * @param match where teams and additional info will be parsed
     * @return sorted teams
     * @throws GameControlException when the maximum players nor the map configuration can't be retrieved.
     * @throws JsonProcessingException when the map configuration can't be parsed.
     */
    Set<MatchDoc.Team> balanceTeams(Match match, Set<MatchAssignable> assignations) throws GameControlException, JsonProcessingException;

}

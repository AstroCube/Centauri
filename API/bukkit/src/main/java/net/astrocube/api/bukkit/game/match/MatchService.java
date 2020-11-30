package net.astrocube.api.bukkit.game.match;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;

import java.util.Set;

public interface MatchService {

    void assignSpectator(String user, String match, boolean join) throws Exception;

    void assignTeams(Set<MatchDoc.Team> teams, String match) throws Exception;

    void assignPending(Set<MatchAssignable> pendingRequests, String match) throws Exception;

    void matchCleanup() throws Exception;

}

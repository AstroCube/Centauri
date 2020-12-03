package net.astrocube.api.bukkit.game.match;

import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;

import java.util.Set;

public interface MatchService {

    void assignSpectator(String user, String match, boolean join) throws Exception;

    void assignTeams(Set<MatchDoc.Team> teams, String match) throws Exception;

    void assignPending(MatchAssignable pendingRequests, String match) throws Exception;

    void matchCleanup() throws Exception;

}

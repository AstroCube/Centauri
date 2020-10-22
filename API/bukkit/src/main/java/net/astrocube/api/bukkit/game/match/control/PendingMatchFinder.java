package net.astrocube.api.bukkit.game.match.control;

import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRequest;
import net.astrocube.api.core.virtual.server.Server;

import java.util.Set;

/**
 * Interface that can be used to find {@link MatchmakingRequest}s
 * were not assigned to a {@link Server} for scheduling.
 */
public interface PendingMatchFinder {

    Set<MatchmakingRequest> getPendingMatches();

}

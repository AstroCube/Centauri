package net.astrocube.api.bukkit.game.match;

import com.fasterxml.jackson.databind.node.ArrayNode;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRequest;


public interface AvailableMatchServerProvider {

    /**
     * @param request
     * @return pai
     * @throws Exception
     */
    ArrayNode getPairableServers(MatchmakingRequest request) throws Exception;

}

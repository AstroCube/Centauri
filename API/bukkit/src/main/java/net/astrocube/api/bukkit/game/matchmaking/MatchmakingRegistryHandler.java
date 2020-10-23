package net.astrocube.api.bukkit.game.matchmaking;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.astrocube.api.core.redis.Redis;

import java.util.Optional;


/**
 * Matchmaking handler who will handle all the requests made
 * by users to join a match.
 */
public interface MatchmakingRegistryHandler {

    /**
     * Generate matchmaking request and register it to {@link Redis}.
     * @param requesters of the matchmaking.
     * @param gameMode to be paired.
     * @param subMode to be paired.
     */
    void generateRequest(MatchAssignable requesters, String gameMode, String subMode) throws JsonProcessingException;

    /**
     * Generate matchmaking request and register it to {@link Redis}.
     * @param requesters of the matchmaking.
     * @param gameMode to be paired.
     * @param subMode to be paired.
     * @param map to be paired.
     */
    void generateRequest(MatchAssignable requesters, String gameMode, String subMode, String map) throws JsonProcessingException;

    /**
     * Generate matchmaking request and register it to {@link Redis}.
     * @param requesters of the matchmaking.
     * @param gameMode to be paired.
     * @param subMode to be paired.
     * @param criteria to be included at the matchmaking request.
     * @param map to be paired.
     */
    void generateRequest(MatchAssignable requesters, String gameMode, String subMode, String map, ObjectNode criteria) throws JsonProcessingException;

}

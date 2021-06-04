package net.astrocube.api.bukkit.game.matchmaking.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRequest;

public interface MatchmakingErrorBroadcaster {

	void broadcastError(MatchmakingRequest request, String message) throws JsonProcessingException;

}

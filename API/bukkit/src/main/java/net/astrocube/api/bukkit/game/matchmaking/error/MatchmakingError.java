package net.astrocube.api.bukkit.game.matchmaking.error;

import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRequest;
import net.astrocube.api.core.message.Message;

public interface MatchmakingError extends Message {

    /**
     * @return {@link MatchmakingRequest} that caused an error
     */
    MatchmakingRequest getRequest();

    /**
     * @return reason of the error
     */
    String getReason();

}

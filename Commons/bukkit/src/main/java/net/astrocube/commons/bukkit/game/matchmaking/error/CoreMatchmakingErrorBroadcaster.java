package net.astrocube.commons.bukkit.game.matchmaking.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRequest;
import net.astrocube.api.bukkit.game.matchmaking.error.MatchmakingError;
import net.astrocube.api.bukkit.game.matchmaking.error.MatchmakingErrorBroadcaster;
import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.message.Messenger;

import java.util.HashMap;

@Singleton
public class CoreMatchmakingErrorBroadcaster implements MatchmakingErrorBroadcaster {

    private final Channel<MatchmakingError> channel;

    @Inject
    public CoreMatchmakingErrorBroadcaster(Messenger messenger) {
        this.channel = messenger.getChannel(MatchmakingError.class);
    }

    @Override
    public void broadcastError(MatchmakingRequest request, String message) throws JsonProcessingException {
        channel.sendMessage(new MatchmakingError() {
            @Override
            public MatchmakingRequest getRequest() {
                return request;
            }

            @Override
            public String getReason() {
                return message;
            }
        }, new HashMap<>());
    }

}

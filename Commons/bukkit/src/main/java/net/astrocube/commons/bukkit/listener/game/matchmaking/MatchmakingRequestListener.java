package net.astrocube.commons.bukkit.listener.game.matchmaking;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.event.matchmaking.MatchmakingErrorEvent;
import net.astrocube.api.bukkit.game.event.matchmaking.MatchmakingRequestEvent;
import net.astrocube.api.bukkit.game.match.MatchAssigner;
import net.astrocube.api.bukkit.game.matchmaking.AvailableMatchProvider;
import net.astrocube.api.bukkit.game.matchmaking.IdealMatchSelector;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRequest;
import net.astrocube.api.bukkit.game.matchmaking.error.MatchmakingError;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

public class MatchmakingRequestListener implements Listener {

    private @Inject AvailableMatchProvider availableMatchProvider;
    private @Inject MatchAssigner matchAssigner;
    private @Inject IdealMatchSelector idealMatchSelector;

    @EventHandler
    public void onMatchmakingRequest(MatchmakingRequestEvent event) {

        try {

            Optional<Match> match = idealMatchSelector.sortAvailableMatches(
                    availableMatchProvider.getCriteriaAvailableMatches(event.getMatchmakingRequest())
            );

            if (match.isPresent()) {
                matchAssigner.assign(event.getMatchmakingRequest().getRequesters(), match.get());
            } else {
                // TODO: Send alert to open new server. Skip if debugging
            }

        } catch (Exception e) {
            Bukkit.getPluginManager().callEvent(new MatchmakingErrorEvent(
                    new MatchmakingError() {
                        @Override
                        public MatchmakingRequest getRequest() {
                            return event.getMatchmakingRequest();
                        }

                        @Override
                        public String getReason() {
                            return e.getMessage();
                        }
                    }
            ));
        }
    }

}

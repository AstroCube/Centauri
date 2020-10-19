package net.astrocube.commons.bukkit.listener.game;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.event.MatchmakingRequestEvent;
import net.astrocube.api.bukkit.game.matchmaking.AvailableMatchProvider;
import net.astrocube.api.bukkit.game.matchmaking.IdealMatchSelector;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Optional;

public class MatchmakingRequestListener implements Listener {

    private @Inject AvailableMatchProvider availableMatchProvider;
    private @Inject IdealMatchSelector idealMatchSelector;

    @EventHandler
    public void onMatchmakingRequest(MatchmakingRequestEvent event) {

        try {

            Optional<Match> match = idealMatchSelector.sortAvailableMatches(
                    availableMatchProvider.getCriteriaAvailableMatches(event.getMatchmakingRequest())
            );

            if (match.isPresent()) {

                // TODO: Send alert to open new server

            } else {

                // TODO: Send the player to the server

            }

        } catch (Exception e) {

            //TODO: Handle error

            e.printStackTrace();
        }
    }

}

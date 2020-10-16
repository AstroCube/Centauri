package net.astrocube.commons.bukkit.listener.game;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.event.MatchmakingRequestEvent;
import net.astrocube.api.bukkit.game.matchmaking.AvailableMatchProvider;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Set;

public class MatchmakingRequestListener implements Listener {

    private @Inject AvailableMatchProvider availableMatchProvider;

    @EventHandler
    public void onMatchmakingRequest(MatchmakingRequestEvent event) {

        try {

            Set<Match> matches = availableMatchProvider.getCriteriaAvailableMatches(event.getMatchmakingRequest());




        } catch (Exception e) {

            //TODO: Handle error

            e.printStackTrace();
        }
    }

}

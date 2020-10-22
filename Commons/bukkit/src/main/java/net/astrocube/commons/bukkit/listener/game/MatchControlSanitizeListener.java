package net.astrocube.commons.bukkit.listener.game;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.event.MatchControlSanitizeEvent;
import net.astrocube.api.bukkit.game.match.control.MatchScheduler;
import net.astrocube.api.bukkit.game.match.control.PendingMatchFinder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class MatchControlSanitizeListener implements Listener {

    private @Inject MatchScheduler matchmakingScheduler;
    private @Inject PendingMatchFinder pendingMatchFinder;

    @EventHandler
    public void onMatchControlSanitize(MatchControlSanitizeEvent event) {

        pendingMatchFinder.getPendingMatches(event.getGameMode(), event.getSubGameMode())
                .forEach(matchmakingScheduler::schedule);

        //TODO: Get WAITING state match requests from server.

    }

}

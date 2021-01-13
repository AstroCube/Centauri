package net.astrocube.commons.bukkit.listener.game;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.event.match.MatchFinishEvent;
import net.astrocube.api.bukkit.game.match.MatchService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MatchFinishListener implements Listener {

    private @Inject MatchService matchService;

    @EventHandler
    public void onMatchFinish(MatchFinishEvent event) {



    }

}

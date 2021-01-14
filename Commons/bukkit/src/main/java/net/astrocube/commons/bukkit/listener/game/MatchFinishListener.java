package net.astrocube.commons.bukkit.listener.game;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.event.match.MatchFinishEvent;
import net.astrocube.api.bukkit.game.event.match.MatchInvalidateEvent;
import net.astrocube.api.bukkit.game.match.MatchService;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class MatchFinishListener implements Listener {

    private @Inject MatchService matchService;
    private @Inject Plugin plugin;

    @EventHandler
    public void onMatchFinish(MatchFinishEvent event) {
        try {
            matchService.assignVictory(event.getMatch(), event.getWinners());
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Error while adjudicating match victory", e);
            Bukkit.getPluginManager().callEvent(new MatchInvalidateEvent(event.getMatch(), false));
        }
    }

}

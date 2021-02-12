package net.astrocube.commons.bukkit.listener.game.management;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.event.match.MatchControlSanitizeEvent;
import net.astrocube.api.bukkit.game.event.match.MatchInvalidateEvent;
import net.astrocube.api.bukkit.game.event.match.MatchStartEvent;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.match.MatchStateUpdater;
import net.astrocube.api.bukkit.game.scheduler.RunningMatchBalancer;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.commons.bukkit.game.GameControlHelper;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.logging.Level;

public class MatchStartListener implements Listener {

    private @Inject FindService<Match> findService;
    private @Inject MatchStateUpdater matchStateUpdater;
    private @Inject RunningMatchBalancer runningMatchBalancer;
    private @Inject GameControlHelper gameControlHelper;
    private @Inject Plugin plugin;

    @EventHandler
    public void onMatchStart(MatchStartEvent event) {

         findService.find(event.getMatch()).callback(callback -> {

             try {

                 if (!callback.isSuccessful() || !callback.getResponse().isPresent()) {
                     throw new GameControlException("The requested start match was not found");
                 }

                 Match match = callback.getResponse().get();
                 matchStateUpdater.updateMatch(match, MatchDoc.Status.RUNNING);
                 runningMatchBalancer.registerMatch(event.getMatch());

                 Optional<GameControlHelper.ModeCompound> compound =
                         gameControlHelper.getService(match.getGameMode(), match.getSubMode());

                 compound.ifPresent(modeCompound -> Bukkit.getPluginManager().callEvent(
                         new MatchControlSanitizeEvent(
                                 modeCompound.getGameMode(),
                                 modeCompound.getSubGameMode()
                         )
                 ));

             } catch (Exception e) {
                 plugin.getLogger().log(Level.SEVERE, "Error while starting a match.");
                 Bukkit.getPluginManager().callEvent(new MatchInvalidateEvent(event.getMatch(), false));
             }

         });

    }

}

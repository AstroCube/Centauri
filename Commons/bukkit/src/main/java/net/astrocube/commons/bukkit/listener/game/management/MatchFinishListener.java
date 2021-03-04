package net.astrocube.commons.bukkit.listener.game.management;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.event.match.MatchControlSanitizeEvent;
import net.astrocube.api.bukkit.game.event.match.MatchFinishEvent;
import net.astrocube.api.bukkit.game.event.match.MatchInvalidateEvent;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.game.match.MatchService;
import net.astrocube.api.bukkit.game.match.control.MatchParticipantsProvider;
import net.astrocube.api.bukkit.game.scheduler.RunningMatchBalancer;
import net.astrocube.api.bukkit.game.spectator.GhostEffectControl;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.commons.bukkit.game.GameControlHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;

public class MatchFinishListener implements Listener {

    private @Inject MatchService matchService;
    private @Inject FindService<Match> findService;
    private @Inject GhostEffectControl ghostEffectControl;
    private @Inject ActualMatchCache actualMatchCache;
    private @Inject RunningMatchBalancer runningMatchBalancer;
    private @Inject GameControlHelper gameControlHelper;
    private @Inject Plugin plugin;

    @EventHandler
    public void onMatchFinish(MatchFinishEvent event) {
        findService.find(event.getMatch()).callback(matchCallback -> {

            try {

                if (!matchCallback.isSuccessful() && !matchCallback.getResponse().isPresent()) {
                    throw new GameControlException("Error while obtaining match");
                }

                Match match = matchCallback.getResponse().get();

                if (match.getStatus() != MatchDoc.Status.RUNNING) {
                    return;
                }

                matchService.assignVictory(event.getMatch(), event.getWinners());
                Set<Player> players = MatchParticipantsProvider.getInvolved(match);
                ghostEffectControl.clearMatch(match.getId());
                MatchParticipantsProvider.getInvolved(match).forEach(p -> actualMatchCache.remove(p.getDatabaseIdentifier()));
                Bukkit.getScheduler().runTaskLater(plugin, () -> players.forEach(player -> player.kickPlayer("")), 200L);
                runningMatchBalancer.releaseMatch(event.getMatch());

                Optional<GameControlHelper.ModeCompound> compound =
                        gameControlHelper.getService(match.getGameMode(), match.getSubMode());

                compound.ifPresent(modeCompound -> Bukkit.getPluginManager().callEvent(
                        new MatchControlSanitizeEvent(
                                modeCompound.getGameMode(),
                                modeCompound.getSubGameMode()
                        )
                ));

                if (runningMatchBalancer.getTotalMatches() == 1 && runningMatchBalancer.isNeedingRestart()) {
                    Bukkit.shutdown();
                }

            } catch (Exception e) {
                plugin.getLogger().log(Level.SEVERE, "Error while adjudicating match victory", e);
                Bukkit.getPluginManager().callEvent(new MatchInvalidateEvent(event.getMatch(), false));
            }

        });
    }

}

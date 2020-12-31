package net.astrocube.commons.bukkit.listener.game;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.event.game.GameReadyEvent;
import net.astrocube.api.bukkit.game.event.game.GameTimerOutEvent;
import net.astrocube.api.bukkit.game.event.match.MatchInvalidateEvent;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.map.GameMapCache;
import net.astrocube.api.bukkit.game.map.GameMapService;
import net.astrocube.api.bukkit.game.map.MatchMapLoader;
import net.astrocube.api.bukkit.game.match.MatchService;
import net.astrocube.api.bukkit.game.match.MatchStateUpdater;
import net.astrocube.api.bukkit.game.match.control.TeamBalancer;
import net.astrocube.api.bukkit.virtual.game.map.GameMap;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.service.update.UpdateService;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;

public class    GameTimerOutListener implements Listener {

    private @Inject FindService<Match> findService;
    private @Inject UpdateService<Match, MatchDoc.Partial> updateService;
    private @Inject MatchStateUpdater matchStateUpdater;
    private @Inject MatchService matchService;
    private @Inject TeamBalancer teamBalancer;
    private @Inject MatchMapLoader matchMapLoader;
    private @Inject GameMapCache gameMapCache;
    private @Inject GameMapService gameMapService;
    private @Inject Plugin plugin;

    @EventHandler
    public void onGameTimeOut(GameTimerOutEvent event) {

        findService.find(event.getMatch()).callback(matchCallback -> {

            try {

                if (!matchCallback.isSuccessful() || !matchCallback.getResponse().isPresent()) {
                    throw new GameControlException("Unable to retrieve match from cache");
                }

                Match match = matchCallback.getResponse().get();

                if (match.getMap() == null) {

                    Optional<GameMap> gameMap = gameMapService
                            .getRandomMap(match.getGameMode(), match.getSubMode());

                    if (!gameMap.isPresent()) {
                        throw new GameControlException("Unable to assign a map for this match");
                    }

                    match.setMap(gameMap.get().getId());
                    updateService.update(match);
                }

                matchMapLoader.loadMatchMap(match);

                String configuration = new String(gameMapCache.getConfiguration(match.getMap()));
                matchStateUpdater.updateMatch(match, MatchDoc.Status.STARTING);

                Set<MatchDoc.Team> balanced = teamBalancer.balanceTeams(match.getPending());

                matchService.assignTeams(balanced, match.getId());

                Bukkit.getPluginManager().callEvent(new GameReadyEvent(event.getMatch(), configuration, balanced));

            } catch (Exception e) {
                Bukkit.getPluginManager().callEvent(
                        new MatchInvalidateEvent(event.getMatch(), false));
                plugin.getLogger().log(Level.SEVERE, "There was an error starting a match", e);
            }

        });

    }

}

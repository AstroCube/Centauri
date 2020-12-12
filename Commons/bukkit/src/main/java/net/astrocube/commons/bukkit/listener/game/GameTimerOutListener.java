package net.astrocube.commons.bukkit.listener.game;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.event.GameReadyEvent;
import net.astrocube.api.bukkit.game.event.GameTimerOutEvent;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.map.GameMapCache;
import net.astrocube.api.bukkit.game.map.GameMapService;
import net.astrocube.api.bukkit.game.map.MatchMapLoader;
import net.astrocube.api.bukkit.virtual.game.map.GameMap;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.find.FindService;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Optional;

public class GameTimerOutListener implements Listener {

    private @Inject FindService<Match> findService;
    private @Inject MatchMapLoader matchMapLoader;
    private @Inject GameMapCache gameMapCache;
    private @Inject GameMapService gameMapService;

    @EventHandler
    public void onGameTimeOut(GameTimerOutEvent event) {

        findService.find(event.getMatch()).callback(matchCallback -> {

            try {

                if (!matchCallback.isSuccessful() || !matchCallback.getResponse().isPresent()) {
                    throw new GameControlException("Unable to retrieve match from cache");
                }

                Match match = matchCallback.getResponse().get();

                if (match.getMap() == null) {

                    Optional<GameMap> gameMap = gameMapService.getRandomMap(match.getGameMode(), match.getSubMode());

                    if (!gameMap.isPresent()) {
                        throw new GameControlException("Unable to assign a map for this match");
                    }

                    match.setMap(gameMap.get().getId());
                }

                matchMapLoader.loadMatchMap(matchCallback.getResponse().get());

                String configuration = new String(gameMapCache.getConfiguration(event.getMatch()));
                Bukkit.getPluginManager().callEvent(new GameReadyEvent(event.getMatch(), configuration));

            } catch (Exception e) {
                // TODO: Throw error and call invalidation event
                e.printStackTrace();
            }

        });


    }

}

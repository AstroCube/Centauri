package net.astrocube.commons.bukkit.listener.game;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.event.GameReadyEvent;
import net.astrocube.api.bukkit.game.event.GameTimerOutEvent;
import net.astrocube.api.bukkit.game.map.GameMapCache;
import net.astrocube.api.bukkit.game.map.MatchMapLoader;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.find.FindService;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameTimerOutListener implements Listener {

    private @Inject FindService<Match> findService;
    private @Inject MatchMapLoader matchMapLoader;
    private @Inject GameMapCache gameMapCache;

    @EventHandler
    public void onGameTimeOut(GameTimerOutEvent event) {

        findService.find(event.getMatch()).callback(matchCallback -> {

            try {

                if (!matchCallback.isSuccessful() || !matchCallback.getResponse().isPresent()) {
                    // TODO: Throw error and call invalidation event
                }

                matchMapLoader.loadMatchMap(matchCallback.getResponse().get());
                String configuration = new String(gameMapCache.getConfiguration(event.getMatch()));
                Bukkit.getPluginManager().callEvent(new GameReadyEvent(event.getMatch(), configuration));

            } catch (Exception e) {
                e.printStackTrace();
            }

        });


    }

}

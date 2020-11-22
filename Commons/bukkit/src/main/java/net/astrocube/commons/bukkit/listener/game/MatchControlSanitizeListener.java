package net.astrocube.commons.bukkit.listener.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.event.MatchControlSanitizeEvent;
import net.astrocube.api.bukkit.game.match.control.MatchScheduler;
import net.astrocube.api.bukkit.game.match.control.PendingMatchFinder;
import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.server.ServerService;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.server.Server;
import net.astrocube.commons.bukkit.game.matchmaking.CoreAvailableMatchProvider;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;


public class MatchControlSanitizeListener implements Listener {

    private @Inject MatchScheduler matchmakingScheduler;
    private @Inject PendingMatchFinder pendingMatchFinder;
    private @Inject ServerService serverService;
    private @Inject ObjectMapper mapper;
    private @Inject QueryService<Match> queryService;
    private @Inject Plugin plugin;

    @EventHandler
    public void onMatchControlSanitize(MatchControlSanitizeEvent event) {

        try {
            Server server = serverService.getActual();

            pendingMatchFinder.getPendingMatches(event.getGameMode(), event.getSubGameMode())
                    .forEach(pending -> {
                        try {
                            matchmakingScheduler.schedule(pending);
                        } catch (Exception e) {
                            //TODO: Send error on pairing
                        }
                    });

            ObjectNode node = mapper.createObjectNode();
            node.put("server", server.getId());
            node.put("gamemode", event.getGameMode().getId());
            node.put("subGamemode", event.getSubGameMode().getId());

            queryService.query(node).callback(callbacks -> {

                if (!callbacks.isSuccessful() || !callbacks.getResponse().isPresent()) {
                    plugin.getLogger().warning("Can not obtain sanitize matches.");
                }

                if (callbacks.getResponse()
                        .get()
                        .getFoundModels()
                        .stream()
                        .anyMatch(match -> match.getStatus() == MatchDoc.Status.LOBBY && CoreAvailableMatchProvider.getRemainingSpace(match) > 0)) {
                    try {
                        matchmakingScheduler.schedule();
                    } catch (Exception e) {
                        plugin.getLogger().log(Level.WARNING, "There was an error trying to create match at sanitizing.", e);
                    }
                }

            });

        } catch (Exception e) {
            plugin.getLogger().severe("There was an error trying to sanitize matches.");
        }

    }



}

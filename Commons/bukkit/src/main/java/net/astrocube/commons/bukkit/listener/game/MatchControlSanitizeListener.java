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
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;


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
                    .forEach(matchmakingScheduler::schedule);

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
                        .anyMatch(match -> match.getStatus() == MatchDoc.Status.LOBBY && getRemainingSpace(match) > 0)) {
                    matchmakingScheduler.schedule();
                }

            });

        } catch (Exception e) {
            plugin.getLogger().severe("There was an error trying to sanitize matches.");
        }

    }

    private int getRemainingSpace(Match match) {
        int total = 0;
        for (MatchAssignable matchAssignable : match.getPending()) {
            total += matchAssignable.getInvolved().size() + 1;
        }
        return total;
    }

}

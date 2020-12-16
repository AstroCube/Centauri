package net.astrocube.commons.bukkit.listener.game;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.event.MatchmakingRequestEvent;
import net.astrocube.api.bukkit.game.match.MatchAssigner;
import net.astrocube.api.bukkit.game.matchmaking.AvailableMatchProvider;
import net.astrocube.api.bukkit.game.matchmaking.IdealMatchSelector;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.logging.Level;

public class MatchmakingRequestListener implements Listener {

    private @Inject AvailableMatchProvider availableMatchProvider;
    private @Inject MatchAssigner matchAssigner;
    private @Inject IdealMatchSelector idealMatchSelector;
    private @Inject FindService<User> findService;
    private @Inject MessageHandler<Player> messageHandler;
    private @Inject Plugin plugin;

    @EventHandler
    public void onMatchmakingRequest(MatchmakingRequestEvent event) {

        try {

            Optional<Match> match = idealMatchSelector.sortAvailableMatches(
                    availableMatchProvider.getCriteriaAvailableMatches(event.getMatchmakingRequest())
            );

            if (match.isPresent()) {
                matchAssigner.assign(event.getMatchmakingRequest().getRequesters(), match.get());
            } else {
                // TODO: Send alert to open new server. Skip if debugging
            }

        } catch (Exception e) {

            findService.find(event.getMatchmakingRequest().getRequesters().getResponsible()).callback(response -> {

                if (response.isSuccessful() && response.getResponse().isPresent()) {

                    Player player = Bukkit.getPlayer(response.getResponse().get().getUsername());

                    player.sendMessage(messageHandler.format(player,
                            messageHandler.get(player, "game.matchmaking.error")));

                } else {
                    plugin.getLogger().log(Level.SEVERE, "Could not find user for error alerting");
                }

            });
        }
    }

}

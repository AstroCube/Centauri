package net.astrocube.commons.bukkit.listener.game.matchmaking;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.event.matchmaking.MatchmakingErrorEvent;
import net.astrocube.api.bukkit.translation.mode.AlertMode;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class MatchmakingErrorListener implements Listener {

    private @Inject FindService<User> findService;
    private @Inject MessageHandler messageHandler;
    private @Inject Plugin plugin;

    @EventHandler
    public void onMatchmakingError(MatchmakingErrorEvent event) {
        findService.find(
                event.getMatchmakingError().getRequest().getRequesters().getResponsible()
        ).callback(response -> {

            if (response.isSuccessful() && response.getResponse().isPresent()) {

                Player player = Bukkit.getPlayer(response.getResponse().get().getUsername());

                messageHandler.send(player, AlertMode.ERROR, "game.matchmaking.error");

            } else {
                plugin.getLogger().log(Level.SEVERE, "Could not find user for error alerting");
            }

        });
    }

}

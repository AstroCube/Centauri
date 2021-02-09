package net.astrocube.commons.bukkit.listener.user;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.match.MatchAssigner;
import net.astrocube.api.core.session.SessionService;
import net.astrocube.api.core.virtual.server.ServerDoc;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import team.unnamed.uboard.ScoreboardManager;

import java.util.logging.Level;

public class UserDisconnectListener implements Listener {

    private @Inject Plugin plugin;
    private @Inject MatchAssigner matchAssigner;
    private @Inject ScoreboardManager scoreboardManager;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onUserLeave(PlayerQuitEvent event) {

            try {

                Player player = event.getPlayer();
                ServerDoc.Type type = ServerDoc.Type.valueOf(plugin.getConfig().getString("server.type"));
                event.setQuitMessage("");

                if (type == ServerDoc.Type.GAME) {
                    matchAssigner.unAssign(player);
                }

                scoreboardManager.getScoreboard(event.getPlayer().getDatabaseIdentifier()).ifPresent(board -> {
                    scoreboardManager.removeScoreboard(player);
                    scoreboardManager.removeScoreboard(board);
                });

            } catch (Exception e) {
                plugin.getLogger().log(Level.SEVERE, "There was an error disconnecting a player from game.", e);
            }

    }
}
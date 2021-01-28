package net.astrocube.commons.bukkit.listener.game.session;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.event.game.GameUserJoinEvent;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.lobby.LobbySessionManager;
import net.astrocube.api.bukkit.game.spectator.SpectatorSessionManager;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.find.FindService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class GameServerJoinListener implements Listener {

    private @Inject FindService<Match> findService;
    private @Inject LobbySessionManager lobbySessionManager;
    private @Inject SpectatorSessionManager spectatorSessionManager;
    private @Inject MessageHandler messageHandler;
    private @Inject Plugin plugin;

    @EventHandler
    public void onGameUserJoin(GameUserJoinEvent event) {

        findService.find(event.getMatch()).callback(matchResponse -> {

            try {

                if (!matchResponse.isSuccessful() || !matchResponse.getResponse().isPresent()) {
                    throw new GameControlException("Match not found");
                }

                switch (event.getOrigin()) {
                    case WAITING: {
                        lobbySessionManager.connectUser(event.getPlayer(), matchResponse.getResponse().get());
                        break;
                    }
                    case SPECTATING: {
                        spectatorSessionManager.provideFunctions(event.getPlayer(), matchResponse.getResponse().get());
                        break;
                    }
                    default: {
                        kickPlayer(event.getPlayer());
                        break;
                    }
                }

            } catch (Exception exception) {
                plugin.getLogger().warning("There was an error while updating the match assignation.");
                kickPlayer(event.getPlayer());
            }

        });



    }

    private void kickPlayer(Player player) {
        player.kickPlayer(ChatColor.RED + messageHandler.get(player, "game.lobby-error"));
    }

}

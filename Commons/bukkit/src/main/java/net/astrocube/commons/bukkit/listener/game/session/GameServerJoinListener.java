package net.astrocube.commons.bukkit.listener.game.session;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.event.game.GameUserJoinEvent;
import net.astrocube.api.bukkit.game.lobby.LobbySessionManager;
import net.astrocube.api.bukkit.game.spectator.SpectatorSessionManager;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.find.FindService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameServerJoinListener implements Listener {

    private @Inject FindService<Match> findService;
    private @Inject LobbySessionManager lobbySessionManager;
    private @Inject SpectatorSessionManager spectatorSessionManager;
    private @Inject MessageHandler<Player> messageHandler;

    @EventHandler
    public void onGameUserJoin(GameUserJoinEvent event) {

        findService.find(event.getMatch()).callback(matchResponse -> {

            if (!matchResponse.isSuccessful() || !matchResponse.getResponse().isPresent()) {
                Bukkit.getLogger().warning("There was an error while updating the match assignation.");
                kickPlayer(event.getPlayer());
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

        });



    }

    private void kickPlayer(Player player) {
        player.kickPlayer(ChatColor.RED + messageHandler.get(player, "game.lobby-error"));
    }

}

package net.astrocube.commons.bukkit.listener.game;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.event.GameUserDisconnectEvent;
import net.astrocube.api.bukkit.game.event.GameUserJoinEvent;
import net.astrocube.api.bukkit.game.lobby.LobbySessionManager;
import net.astrocube.api.bukkit.game.match.UserMatchJoiner;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.find.FindService;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class LobbyUserDisconnectListener implements Listener {

    private @Inject LobbySessionManager lobbySessionManager;
    private @Inject FindService<Match> findService;
    private @Inject Plugin plugin;

    @EventHandler
    public void onGameUserJoin(GameUserDisconnectEvent event) {
        findService.find(event.getMatch()).callback(matchResponse -> {

            if (!matchResponse.isSuccessful() || !matchResponse.getResponse().isPresent()) {
                plugin.getLogger().warning("There was an error while updating the match assignation.");
            }

            lobbySessionManager.disconnectUser(event.getPlayer(), matchResponse.getResponse().get());
        });
    }

}

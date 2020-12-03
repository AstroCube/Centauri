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
    private @Inject Plugin plugin;

    @EventHandler
    public void onGameUserJoin(GameUserDisconnectEvent event) {
        try {
            lobbySessionManager.disconnectUser(event.getPlayer(), event.getMatch());
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "There was an error disconnecting user", e);
        }
    }

}

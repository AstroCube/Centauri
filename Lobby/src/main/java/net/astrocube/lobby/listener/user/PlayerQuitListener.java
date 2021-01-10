package net.astrocube.lobby.listener.user;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.lobby.nametag.LobbyNametagHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private @Inject LobbyNametagHandler lobbyNametagHandler;

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        lobbyNametagHandler.remove(event.getPlayer());
    }

}

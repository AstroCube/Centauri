package net.astrocube.lobby.listener.user;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.lobby.nametag.LobbyNametagHandler;
import net.astrocube.api.bukkit.lobby.selector.npc.SelectorRegistry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private @Inject LobbyNametagHandler lobbyNametagHandler;
    private @Inject SelectorRegistry selectorRegistry;

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        lobbyNametagHandler.remove(event.getPlayer());
        selectorRegistry.despawnSelectors(event.getPlayer());
    }

}

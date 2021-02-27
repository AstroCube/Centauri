package net.astrocube.commons.bukkit.listener.game.spectator;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.spectator.SpectatorLobbyTeleporter;
import net.astrocube.api.bukkit.user.inventory.event.ActionableItemEvent;
import net.astrocube.api.core.cloud.CloudTeleport;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.plugin.Plugin;

public class LobbyReturnListener implements Listener {

    private @Inject SpectatorLobbyTeleporter spectatorLobbyTeleporter;

    @EventHandler
    public void onGameGadgetInteract(ActionableItemEvent event) {
        if (
                event.getAction().equalsIgnoreCase("gc_lobby") &&
                        (event.getClick() == Action.RIGHT_CLICK_AIR || event.getClick() == Action.RIGHT_CLICK_BLOCK)
        ) {
            if (!spectatorLobbyTeleporter.hasScheduledTeleport(event.getPlayer())) {
                spectatorLobbyTeleporter.cancelTeleport(event.getPlayer());
            } else {
                spectatorLobbyTeleporter.scheduleTeleport(event.getPlayer());
            }
        }
    }
}

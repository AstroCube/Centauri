package net.astrocube.commons.bukkit.listener.game.spectator;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.user.inventory.event.ActionableItemEvent;
import net.astrocube.api.core.cloud.CloudTeleport;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.plugin.Plugin;

public class LobbyReturnListener implements Listener {

    private @Inject CloudTeleport cloudTeleport;
    private @Inject Plugin plugin;

    @EventHandler
    public void onGameGadgetInteract(ActionableItemEvent event) {
        if (
                event.getAction().equalsIgnoreCase("gc_lobby") &&
                        (event.getClick() == Action.RIGHT_CLICK_AIR || event.getClick() == Action.RIGHT_CLICK_BLOCK)
        ) {
            cloudTeleport.teleportToGroup(
                    plugin.getConfig().getString("server.fallback", "main-lobby"), event.getPlayer().getName()
            );
        }
    }
}

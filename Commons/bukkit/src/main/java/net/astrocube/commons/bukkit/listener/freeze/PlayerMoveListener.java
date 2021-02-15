package net.astrocube.commons.bukkit.listener.freeze;

import net.astrocube.api.bukkit.punishment.freeze.FrozenUserProvider;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import javax.inject.Inject;

public class PlayerMoveListener implements Listener {

    private @Inject FrozenUserProvider frozenUserProvider;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        if (frozenUserProvider.isFrozen(event.getPlayer())) {
            event.setCancelled(true);
        }

    }
}
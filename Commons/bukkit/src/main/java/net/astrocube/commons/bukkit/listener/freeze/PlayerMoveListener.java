package net.astrocube.commons.bukkit.listener.freeze;

import net.astrocube.api.bukkit.punishment.freeze.FrozenUserProvider;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import javax.inject.Inject;

public class PlayerMoveListener implements Listener {

    private @Inject FrozenUserProvider frozenUserProvider;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        if (frozenUserProvider.isFrozen(event.getPlayer())) {

            Location from = event.getFrom();
            Location to = event.getTo();

            if(from.getBlockX() != to.getBlockX() || from.getBlockZ() != to.getBlockZ()){
                to.setX(from.getBlockX() + .5);
                to.setZ(from.getBlockZ() + .5);

                event.setTo(to);
            }

        }

    }
}
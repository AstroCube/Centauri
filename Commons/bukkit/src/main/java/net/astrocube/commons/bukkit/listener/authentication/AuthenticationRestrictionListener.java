package net.astrocube.commons.bukkit.listener.authentication;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

public class AuthenticationRestrictionListener implements Listener {

    private @Inject MessageHandler messageHandler;
    private @Inject Plugin plugin;

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        messageHandler.send(event.getPlayer(), AlertModes.ERROR, "authentication.chat");
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onCommandPreProcess(PlayerCommandPreprocessEvent event) {
        String[] command = event.getMessage().split(" ");

        if (command.length == 2) {

            if (command[0].equals("/login") || command[0].equals("/register")) {
                return;
            }

        }

        messageHandler.send(event.getPlayer(), AlertModes.ERROR, "authentication.chat");
        event.setCancelled(true);

    }

    @EventHandler
    public void authenticationMovementListener(PlayerMoveEvent event) {
        if (plugin.getConfig().getBoolean("authentication.enabled")) {
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

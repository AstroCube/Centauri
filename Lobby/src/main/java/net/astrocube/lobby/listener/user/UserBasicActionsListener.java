package net.astrocube.lobby.listener.user;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Locale;

public class UserBasicActionsListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamage(EntityDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onItemDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInventoryInteract(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCraftTableInteract(PlayerInteractEvent event) {

        Block soilBlock = event.getPlayer().getLocation().getBlock();
        if ((event.getAction() == Action.PHYSICAL)) {
            if (soilBlock.getType() == Material.SOIL) {
                event.setCancelled(true);
                return;
            }
        }

        if (
                (event.getMaterial() == Material.WORKBENCH) &&
                (event.getMaterial().toString().toLowerCase(Locale.ROOT).contains("door")) &&
                (event.getMaterial() == Material.ANVIL) &&
                (event.getMaterial() == Material.FURNACE || event.getMaterial() == Material.BURNING_FURNACE) &&
                (event.getMaterial() == Material.CHEST) &&
                (event.getMaterial() == Material.STONE_BUTTON || event.getMaterial() == Material.WOOD_BUTTON)
        ) {
            event.setCancelled(true);
        }
    }


}

package net.astrocube.commons.bukkit.listener.inventory;

import net.astrocube.api.bukkit.user.inventory.event.ActionableItemEvent;
import net.astrocube.api.bukkit.user.inventory.nbt.NBTUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class PlayerHotbarClickListener implements Listener {

    @EventHandler
    public void onHotbarClick(PlayerInteractEvent event) {

        @Nullable ItemStack stack = event.getItem();

        if (
                stack != null &&
                stack.getData().getItemType() == Material.AIR &&
                NBTUtils.hasString(stack, "actionable")
        ) {
            Bukkit.getPluginManager().callEvent(new ActionableItemEvent(
                    event.getPlayer(),
                    NBTUtils.getString(stack, "actionable"),
                    event.getAction()
            ));
            event.setCancelled(true);
        }
    }

}

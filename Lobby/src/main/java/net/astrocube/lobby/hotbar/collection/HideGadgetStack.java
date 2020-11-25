package net.astrocube.lobby.hotbar.collection;

import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.user.inventory.nbt.NBTUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HideGadgetStack {

    public static ItemStack get(MessageHandler<Player> provider, Player player, boolean active) {

        ItemStack hidingMenuBase;
        if (active) {
            hidingMenuBase = NBTUtils.addString(
                    new ItemStack(Material.GLOWSTONE_DUST, 1),
                    "actionable",
                    "hide_gadget"
            );
        } else {
            hidingMenuBase = NBTUtils.addString(
                    new ItemStack(Material.REDSTONE, 1),
                    "actionable",
                    "hide_gadget"
            );
        }

        ItemMeta hidingMenuMeta = hidingMenuBase.getItemMeta();
        if (active) {
            hidingMenuMeta.setDisplayName(
                    provider.get(player, "lobby.hiding.active-gadget")
            );
        } else {
            hidingMenuMeta.setDisplayName(
                    provider.get(player, "lobby.hiding.disabled-gadget")
            );
        }

        hidingMenuMeta.setLore(provider.getMany(player, "lobby.hiding.gadget-lore"));
        hidingMenuBase.setItemMeta(hidingMenuMeta);
        return hidingMenuBase;
    }
}

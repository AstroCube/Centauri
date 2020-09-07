package net.astrocube.lobby.hotbar.collection;

import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.user.inventory.nbt.NBTUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GameGadgetStack {

    public static ItemStack get(MessageHandler<Player> provider, Player player) {
        ItemStack gameMenuBase = NBTUtils.addString(
                new ItemStack(Material.COMPASS, 1),
                "actionable",
                "game_menu"
        );

        ItemMeta gameMenuMeta = gameMenuBase.getItemMeta();
        gameMenuMeta.setDisplayName(
                provider.getMessage(player, "lobby.gameSelector.gadget")
        );
        gameMenuMeta.setLore(
                provider.getMessages(player, "lobby.gameSelector.gadget-lore")
        );
        gameMenuBase.setItemMeta(gameMenuMeta);
        return gameMenuBase;
    }
}

package net.astrocube.lobby.hotbar.collection;

import me.yushust.message.core.MessageProvider;
import net.astrocube.api.bukkit.user.inventory.nbt.NBTUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GameGadgetStack {

    public static ItemStack get(MessageProvider<Player> provider, Player player) {

        /*
        ItemStack gameMenuBase = NBTUtils.addString(
                new ItemStack(Material.COMPASS, 1),
                "actionable",
                "game_menu"
        );

        ItemMeta gameMenuMeta = gameMenuBase.getItemMeta();
        gameMenuMeta.setDisplayName(
                ChatColor.YELLOW +
                        this.translatableField.getField(
                                l,
                                "commons_lobby_game_menu"
                        ) +
                        ChatColor.GRAY
                        + "(" +
                        this.translatableField.getUnspacedField(l, "commons_right_click")
                        + ")"
        );
        loreDisplayArray.add(
                this.translatableField.getUnspacedField(
                        l,
                        "commons_lobby_game_description"
                ) + ".",
                ChatColor.GRAY
        );
        gameMenuMeta.setLore(loreDisplayArray);
        gameMenuBase.setItemMeta(gameMenuMeta);
         */
        return null;
    }
}

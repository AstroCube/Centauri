package net.astrocube.commons.bukkit.menu.admin;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.GUIBuilder;
import team.unnamed.gui.core.item.type.ItemBuilder;

public class AdminMainPageMenu {

    public Inventory createAdminPanel(Player player) {

        return GUIBuilder
                .builder("Admin Panel", 3)
                .addItem(ItemClickable
                        .builder(11)
                        .setItemStack(ItemBuilder.newBuilder(Material.PAPER)
                                .setName("Find match name")
                                .setLore("lore")
                                .build())
                        .setAction(event -> {
                            player.sendMessage("Open the match panel");
                            return true;
                        })
                        .build())

                .addItem(ItemClickable.builder(13)
                        .setItemStack(ItemBuilder.newBuilder(Material.COMPASS)
                                .setName("Lobby TP")
                                .setLore("lore")
                                .build())
                        .setAction(event -> {
                            player.sendMessage("Open the lobby TP panel");
                            return true;
                        })
                        .build())
                .build();
    }
}
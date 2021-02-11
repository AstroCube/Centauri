package net.astrocube.commons.bukkit.admin;

import me.yushust.message.MessageHandler;
import net.astrocube.commons.bukkit.admin.selector.AdminGameModeSelectorMenu;
import net.astrocube.commons.bukkit.admin.selector.AdminOnlineStaffMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.GUIBuilder;
import team.unnamed.gui.core.item.type.ItemBuilder;

import javax.inject.Inject;

public class AdminMainPageMenu {

    @Inject
    private AdminGameModeSelectorMenu adminGameModeSelectorMenu;
    @Inject
    private AdminOnlineStaffMenu adminOnlineStaffMenu;
    @Inject
    private MessageHandler playerMessageHandler;

    public Inventory createAdminPanel(Player player) {

        return GUIBuilder
                .builder(playerMessageHandler.get(player, "admin-panel.main.title"), 3)
                .addItem(ItemClickable
                        .builder(11)
                        .setItemStack(ItemBuilder.newBuilder(Material.PAPER)
                                .setName(playerMessageHandler.get(player, "admin-panel.main.items.match-teleport.name"))
                                .setLore(playerMessageHandler.getMany(player, "admin-panel.main.items.match-teleport.lore"))
                                .build())
                        .setAction(event -> {
                            adminGameModeSelectorMenu.createGameModeSelectorMenu(player);
                            return true;
                        })
                        .build())

                .addItem(ItemClickable.builder(13)
                        .setItemStack(ItemBuilder.newBuilder(Material.COMPASS)
                                .setName(playerMessageHandler.get(player, "admin-panel.main.items.lobby-teleport.name"))
                                .setLore(playerMessageHandler.getMany(player, "admin-panel.main.items.lobby-teleport.lore"))
                                .build())
                        .setAction(event -> {
                            player.sendMessage("Open the lobby TP panel");
                            return true;
                        })
                        .build())

                .addItem(ItemClickable.builder(15)
                        .setItemStack(ItemBuilder.newBuilder(Material.ANVIL)
                                .setName(playerMessageHandler.get(player, "admin-panel.main.items.online-staff.name"))
                                .setLore(playerMessageHandler.getMany(player, "admin-panel.main.items.online-staff.lore"))
                                .build())
                        .setAction(event -> {
                            adminOnlineStaffMenu.createOnlineStaffMenu(player, 1);
                            return true;
                        })
                        .build())
                .build();
    }
}
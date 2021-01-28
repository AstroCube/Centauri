package net.astrocube.commons.bukkit.menu.punishment;

import me.yushust.message.MessageHandler;
import net.astrocube.api.core.punishment.PunishmentBuilder;
import net.astrocube.api.core.virtual.punishment.PunishmentDoc;
import net.astrocube.commons.core.punishment.CorePunishmentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.GUIBuilder;
import team.unnamed.gui.core.item.type.ItemBuilder;

import javax.inject.Inject;

public class PunishmentChooserMenu {

    @Inject
    private MessageHandler messageHandler;
    @Inject
    private PunishmentReasonChooserMenu punishmentReasonChooserMenu;

    public Inventory createPunishmentChooserMenu(Player player, String punished) {

        return GUIBuilder.builder(messageHandler.get(player, "punish-menu.title"), 3)
                .addItem(ItemClickable.builder(11)
                        .setItemStack(ItemBuilder.newBuilder(Material.REDSTONE_BLOCK)
                                .setName(messageHandler.replacing(player, "punish-menu.items.ban.name", "%punished_name%", punished))
                                .setLore(messageHandler.replacingMany(player, "punish-menu.items.ban.lore", "%punished_name%", punished))
                                .build())
                        .setAction(inventoryClickEvent -> {
                            PunishmentBuilder punishmentBuilder = CorePunishmentBuilder.newBuilder(inventoryClickEvent.getWhoClicked().getName(), punished, PunishmentDoc.Identity.Type.BAN);
                            player.openInventory(punishmentReasonChooserMenu.createPunishmentReasonChooserMenu(player, punishmentBuilder));
                            return true;
                        })
                        .build()
                )
                .addItem(ItemClickable.builder(13)
                        .setItemStack(ItemBuilder.newBuilder(Material.REDSTONE_BLOCK)
                                .setName(messageHandler.replacing(player, "punish-menu.items.kick.name", "%punished_name%", punished))
                                .setLore(messageHandler.replacingMany(player, "punish-menu.items.kick.lore", "%punished_name%", punished))
                                .build())
                        .setAction(inventoryClickEvent -> {
                            PunishmentBuilder punishmentBuilder = CorePunishmentBuilder.newBuilder(inventoryClickEvent.getWhoClicked().getName(), punished, PunishmentDoc.Identity.Type.KICK);
                            player.openInventory(punishmentReasonChooserMenu.createPunishmentReasonChooserMenu(player, punishmentBuilder));
                            return true;
                        })
                        .build()
                )
                .addItem(ItemClickable.builder(15)
                        .setItemStack(ItemBuilder.newBuilder(Material.REDSTONE_BLOCK)
                                .setName(messageHandler.replacing(player, "punish-menu.items.warn.name", "%punished_name%", punished))
                                .setLore(messageHandler.replacingMany(player, "punish-menu.items.warn.lore", "%punished_name%", punished))
                                .build())
                        .setAction(inventoryClickEvent -> {
                            PunishmentBuilder punishmentBuilder = CorePunishmentBuilder.newBuilder(inventoryClickEvent.getWhoClicked().getName(), punished, PunishmentDoc.Identity.Type.WARN);
                            player.openInventory(punishmentReasonChooserMenu.createPunishmentReasonChooserMenu(player, punishmentBuilder));
                            return true;
                        })
                        .build()
                )
                .addItem(ItemClickable.builder(18)
                        .setItemStack(ItemBuilder.newBuilder(Material.ICE)
                                .setName(messageHandler.replacing(player, "punish-menu.items.freeze.name", "%punished_name%", punished))
                                .setLore(messageHandler.replacingMany(player, "punish-menu.items.freeze.lore", "%punished_name%", punished))
                                .build())
                        .setAction(inventoryClickEvent -> {

                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/freeze " + punished);
                            return true;
                        })
                        .build()
                )
                .build();
    }
}
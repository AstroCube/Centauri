package net.astrocube.commons.bukkit.menu.punishment;

import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.core.punishment.PunishmentBuilder;
import net.astrocube.api.core.virtual.group.Group;
import net.astrocube.api.core.virtual.punishment.PunishmentDoc;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.core.punishment.CorePunishmentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.GUIBuilder;
import team.unnamed.gui.core.item.type.ItemBuilder;

import javax.inject.Inject;
import java.util.Locale;

public class PunishmentChooserMenu {

    private @Inject MessageHandler messageHandler;
    private @Inject PunishmentReasonChooserMenu punishmentReasonChooserMenu;
    private @Inject DisplayMatcher displayMatcher;

    public Inventory createPunishmentChooserMenu(Player player, User target) {

        Group.Flair flair = displayMatcher.getRealmDisplay(target);
        String resumedName = ChatColor.GRAY + target.getDisplay();

        try {
            ChatColor color = ChatColor.valueOf(flair.getColor().toUpperCase(Locale.ROOT));
            resumedName = color + target.getDisplay();
        } catch (IllegalArgumentException ignore) {}

        return GUIBuilder.builder(messageHandler.get(player, "punish-menu.title"), 3)
                .addItem(ItemClickable.builder(11)
                        .setItemStack(ItemBuilder.newBuilder(Material.REDSTONE_BLOCK)
                                .setName(messageHandler.replacing(player, "punish-menu.items.ban.name", "%punished_name%", resumedName))
                                .setLore(messageHandler.replacingMany(player, "punish-menu.items.ban.lore", "%punished_name%", resumedName))
                                .build())
                        .setAction(inventoryClickEvent -> {
                            PunishmentBuilder punishmentBuilder = CorePunishmentBuilder.newBuilder(inventoryClickEvent.getWhoClicked().getName(), target.getUsername(), PunishmentDoc.Identity.Type.BAN);
                            player.openInventory(punishmentReasonChooserMenu.createPunishmentReasonChooserMenu(player, punishmentBuilder));
                            return true;
                        })
                        .build()
                )
                .addItem(ItemClickable.builder(13)
                        .setItemStack(ItemBuilder.newBuilder(Material.REDSTONE_BLOCK)
                                .setName(messageHandler.replacing(player, "punish-menu.items.kick.name", "%punished_name%", resumedName))
                                .setLore(messageHandler.replacingMany(player, "punish-menu.items.kick.lore", "%punished_name%", resumedName))
                                .build())
                        .setAction(inventoryClickEvent -> {
                            PunishmentBuilder punishmentBuilder = CorePunishmentBuilder.newBuilder(inventoryClickEvent.getWhoClicked().getName(), target.getUsername(), PunishmentDoc.Identity.Type.KICK);
                            player.openInventory(punishmentReasonChooserMenu.createPunishmentReasonChooserMenu(player, punishmentBuilder));
                            return true;
                        })
                        .build()
                )
                .addItem(ItemClickable.builder(15)
                        .setItemStack(ItemBuilder.newBuilder(Material.REDSTONE_BLOCK)
                                .setName(messageHandler.replacing(player, "punish-menu.items.warn.name", "%punished_name%", resumedName))
                                .setLore(messageHandler.replacingMany(player, "punish-menu.items.warn.lore", "%punished_name%", resumedName))
                                .build())
                        .setAction(inventoryClickEvent -> {
                            PunishmentBuilder punishmentBuilder = CorePunishmentBuilder.newBuilder(inventoryClickEvent.getWhoClicked().getName(), target.getUsername(), PunishmentDoc.Identity.Type.WARN);
                            player.openInventory(punishmentReasonChooserMenu.createPunishmentReasonChooserMenu(player, punishmentBuilder));
                            return true;
                        })
                        .build()
                )
                .addItem(ItemClickable.builder(18)
                        .setItemStack(ItemBuilder.newBuilder(Material.ICE)
                                .setName(messageHandler.replacing(player, "punish-menu.items.freeze.name", "%punished_name%", resumedName))
                                .setLore(messageHandler.replacingMany(player, "punish-menu.items.freeze.lore", "%punished_name%", resumedName))
                                .build())
                        .setAction(inventoryClickEvent -> {

                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/freeze " + target.getUsername());
                            return true;
                        })
                        .build()
                )
                .build();
    }
}
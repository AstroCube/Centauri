package net.astrocube.commons.bukkit.menu.punishment;

import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.core.punishment.PunishmentBuilder;
import net.astrocube.api.core.virtual.group.Group;
import net.astrocube.api.core.virtual.punishment.PunishmentDoc;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.bukkit.menu.GenericHeadHelper;
import net.astrocube.commons.bukkit.menu.HeadLibrary;
import net.astrocube.commons.bukkit.menu.MenuUtils;
import net.astrocube.commons.core.punishment.CorePunishmentBuilder;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.GUIBuilder;
import team.unnamed.gui.core.item.type.ItemBuilder;

import javax.inject.Inject;
import java.util.Locale;

public class PunishmentChooserMenu {

    private @Inject MessageHandler messageHandler;
    private @Inject PunishmentReasonChooserMenu punishmentReasonChooserMenu;
    private @Inject GenericHeadHelper genericHeadHelper;
    private @Inject DisplayMatcher displayMatcher;
    private @Inject Plugin plugin;

    public Inventory createPunishmentChooserMenu(Player player, User issuer, User target) {

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
                            PunishmentBuilder punishmentBuilder = CorePunishmentBuilder.newBuilder(issuer, target, PunishmentDoc.Identity.Type.BAN);
                            player.openInventory(punishmentReasonChooserMenu.createFilter(player, punishmentBuilder, 1));
                            return true;
                        })
                        .build()
                )
                .addItem(ItemClickable.builder(13)
                        .setItemStack(ItemBuilder.newBuilder(Material.GOLD_BLOCK)
                                .setName(messageHandler.replacing(player, "punish-menu.items.kick.name", "%punished_name%", resumedName))
                                .setLore(messageHandler.replacingMany(player, "punish-menu.items.kick.lore", "%punished_name%", resumedName))
                                .build())
                        .setAction(inventoryClickEvent -> {
                            PunishmentBuilder punishmentBuilder = CorePunishmentBuilder.newBuilder(issuer, target, PunishmentDoc.Identity.Type.KICK);
                            player.openInventory(punishmentReasonChooserMenu.createFilter(player, punishmentBuilder, 1));
                            return true;
                        })
                        .build()
                )
                .addItem(ItemClickable.builder(15)
                        .setItemStack(ItemBuilder.newBuilder(Material.EMERALD_BLOCK)
                                .setName(messageHandler.replacing(player, "punish-menu.items.warn.name", "%punished_name%", resumedName))
                                .setLore(messageHandler.replacingMany(player, "punish-menu.items.warn.lore", "%punished_name%", resumedName))
                                .build())
                        .setAction(inventoryClickEvent -> {
                            PunishmentBuilder punishmentBuilder = CorePunishmentBuilder.newBuilder(issuer, target, PunishmentDoc.Identity.Type.WARN);
                            player.openInventory(punishmentReasonChooserMenu.createFilter(player, punishmentBuilder, 1));
                            return true;
                        })
                        .build()
                )
                .addItem(
                        genericHeadHelper.generateDefaultClickable(
                                generateSearchItem(player),
                                26,
                                ClickType.LEFT,
                                p -> {
                                    new AnvilGUI.Builder()
                                            .title(messageHandler.get(player, "punish-menu.search.title"))
                                            .text(messageHandler.get(player, "punish-menu.search.write"))
                                            .onClose(close ->
                                                    player.openInventory(createPunishmentChooserMenu(player, issuer, target))
                                            )
                                            .onComplete((playerIgnored, text) -> {
                                                PunishmentBuilder punishmentBuilder =
                                                        CorePunishmentBuilder.newBuilder(issuer, target, null);
                                                player.openInventory(punishmentReasonChooserMenu.createSearch(player, punishmentBuilder, text, 1));
                                                return AnvilGUI.Response.close();
                                            })
                                            .plugin(plugin)
                                            .open(player);
                                }
                        )
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

    private ItemStack generateSearchItem(Player player) {
        ItemStack stack = MenuUtils.generateHead(HeadLibrary.BOOK);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(messageHandler.get(player, "punish-menu.search.item.title"));
        meta.setLore(messageHandler.getMany(player, "punish-menu.search.item.lore"));
        stack.setItemMeta(meta);
        return stack;
    }

}
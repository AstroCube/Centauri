package net.astrocube.commons.bukkit.menu;

import net.astrocube.api.core.punishment.PunishmentBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.type.GUIBuilder;
import team.unnamed.gui.core.item.type.ItemBuilder;

import javax.inject.Inject;

public class PunishmentReasonChooserMenu {

    @Inject private Plugin plugin;
    @Inject PunishmentExpirationChooserMenu punishmentExpirationChooserMenu;

    public Inventory createPunishmentReasonChooserMenu(Player player,
                                                       PunishmentBuilder punishmentBuilder) {

        GUIBuilder<ItemClickable> guiBuilder = GUIBuilder.builder(plugin.getConfig().getString("punishment-reason-menu.title"), 3);

        switch (punishmentBuilder.getType()) {

            case BAN:

                for (String s : plugin.getConfig().getConfigurationSection("ban-reasons").getKeys(false)) {

                    guiBuilder.addItem(ItemClickable.builder(plugin.getConfig().getInt(s + ".slot"))
                            .setItemStack(ItemBuilder.newBuilder(Material.PAPER)
                                    .setName(plugin.getConfig().getString(s + ".name"))
                                    .setLore(plugin.getConfig().getStringList(s + ".lore"))
                                    .build())
                            .setAction(inventoryClickEvent -> {
                                punishmentBuilder.setReason(s + ".punish-reason");
                                player.openInventory(punishmentExpirationChooserMenu
                                        .createPunishmentExpirationChooserMenu(player, punishmentBuilder));
                                return true;
                            })
                            .build());
                }

                break;
            case KICK:

                for (String s : plugin.getConfig().getConfigurationSection("kick-reasons").getKeys(false)) {

                    guiBuilder.addItem(ItemClickable.builder(plugin.getConfig().getInt(s + ".slot"))
                            .setItemStack(ItemBuilder.newBuilder(Material.PAPER)
                                    .setName(plugin.getConfig().getString(s + ".name"))
                                    .setLore(plugin.getConfig().getStringList(s + ".lore"))
                                    .build())
                            .setAction(inventoryClickEvent -> {
                                punishmentBuilder.setReason(s + ".punish-reason");
                                player.openInventory(punishmentExpirationChooserMenu
                                        .createPunishmentExpirationChooserMenu(player, punishmentBuilder));
                                return true;
                            })
                            .build());
                }

                break;
            case WARN:

                for (String s : plugin.getConfig().getConfigurationSection("warn-reasons").getKeys(false)) {

                    guiBuilder.addItem(ItemClickable.builder(plugin.getConfig().getInt(s + ".slot"))
                            .setItemStack(ItemBuilder.newBuilder(Material.PAPER)
                                    .setName(plugin.getConfig().getString(s + ".name"))
                                    .setLore(plugin.getConfig().getStringList(s + ".lore"))
                                    .build())
                            .setAction(inventoryClickEvent -> {
                                punishmentBuilder.setReason(s + ".punish-reason");
                                player.openInventory(punishmentExpirationChooserMenu
                                        .createPunishmentExpirationChooserMenu(player, punishmentBuilder));
                                return true;
                            })
                            .build());
                }

                break;
            default:
                guiBuilder.build();
        }
        return guiBuilder.build();
    }
}
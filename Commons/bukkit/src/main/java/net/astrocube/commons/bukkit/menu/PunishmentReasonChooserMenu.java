package net.astrocube.commons.bukkit.menu;

import net.astrocube.api.core.punishment.PunishmentBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.GUIBuilder;
import team.unnamed.gui.core.item.type.ItemBuilder;

import javax.inject.Inject;
import java.util.List;

public class PunishmentReasonChooserMenu {

    @Inject private Plugin plugin;
    @Inject private PunishmentExpirationChooserMenu punishmentExpirationChooserMenu;

    public Inventory createPunishmentReasonChooserMenu(Player player,
                                                       PunishmentBuilder punishmentBuilder) {

        GUIBuilder guiBuilder = GUIBuilder.builder(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("punishment-reason-menu.title")), 3);

        switch (punishmentBuilder.getType()) {

            case BAN:

                for (String s : plugin.getConfig().getConfigurationSection("ban-reasons").getKeys(false)) {

                    guiBuilder.addItem(ItemClickable.builder(plugin.getConfig().getInt("ban-reasons." + s + ".slot"))
                            .setItemStack(ItemBuilder.newBuilder(Material.PAPER)
                                    .setName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ban-reasons." + s + ".name")))
                                    .setLore(colorize(plugin.getConfig().getStringList("ban-reasons." + s + ".lore")))
                                    .build())
                            .setAction(inventoryClickEvent -> {
                                punishmentBuilder.setReason("ban-reasons." + s + ".punish-reason");
                                player.openInventory(punishmentExpirationChooserMenu
                                        .createPunishmentExpirationChooserMenu(player, punishmentBuilder));
                                return true;
                            })
                            .build());
                }

                break;
            case KICK:

                for (String s : plugin.getConfig().getConfigurationSection("kick-reasons").getKeys(false)) {

                    guiBuilder.addItem(ItemClickable.builder(plugin.getConfig().getInt("kick-reasons." + s + ".slot"))
                            .setItemStack(ItemBuilder.newBuilder(Material.PAPER)
                                    .setName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("kick-reasons." + s + ".name")))
                                    .setLore(colorize(plugin.getConfig().getStringList("kick-reasons." + s + ".lore")))
                                    .build())
                            .setAction(inventoryClickEvent -> {
                                punishmentBuilder.setReason("kick-reasons." + s + ".punish-reason");
                                player.openInventory(punishmentExpirationChooserMenu
                                        .createPunishmentExpirationChooserMenu(player, punishmentBuilder));
                                return true;
                            })
                            .build());
                }

                break;
            case WARN:

                for (String s : plugin.getConfig().getConfigurationSection("warn-reasons").getKeys(false)) {

                    guiBuilder.addItem(ItemClickable.builder(plugin.getConfig().getInt("warn-reasons." + s + ".slot"))
                            .setItemStack(ItemBuilder.newBuilder(Material.PAPER)
                                    .setName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("warn-reasons." + s + ".name")))
                                    .setLore(colorize(plugin.getConfig().getStringList("warn-reasons." + s + ".lore")))
                                    .build())
                            .setAction(inventoryClickEvent -> {
                                punishmentBuilder.setReason("warn-reasons." + s + ".punish-reason");
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

    private List<String> colorize(List<String> list) {
        list.replaceAll(line -> ChatColor.translateAlternateColorCodes('&', line));
        return list;
    }
}
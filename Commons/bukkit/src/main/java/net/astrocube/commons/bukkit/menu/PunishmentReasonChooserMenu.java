package net.astrocube.commons.bukkit.menu;

import net.astrocube.api.core.punishment.PunishmentBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.type.GUIBuilder;
import team.unnamed.gui.core.item.type.ItemBuilder;

import javax.inject.Inject;
import javax.inject.Named;

public class PunishmentReasonChooserMenu {

    @Inject @Named("menus") private FileConfiguration menus;

    public Inventory createPunishmentReasonChooserMenu(Player player,
                                                       PunishmentBuilder punishmentBuilder) {

        GUIBuilder<ItemClickable> guiBuilder = GUIBuilder.builder(menus.getString("punishment-reason-menu.title"), 3);

        switch (punishmentBuilder.getType()) {

            case BAN:

                for (String s : menus.getConfigurationSection("ban-reasons").getKeys(false)) {

                    guiBuilder.addItem(ItemClickable.builder(menus.getInt(s + ".slot"))
                            .setItemStack(ItemBuilder.newBuilder(Material.PAPER)
                                    .setName(menus.getString(s + ".name"))
                                    .setLore(menus.getStringList(s + ".lore"))
                                    .build())
                            .setAction(inventoryClickEvent -> {
                                punishmentBuilder.setReason(s + ".punish-reason");
                                //Open the menu to choose the time, and
                                return true;
                            })
                            .build());
                }

                break;
            case KICK:

                for (String s : menus.getConfigurationSection("kick-reasons").getKeys(false)) {

                    guiBuilder.addItem(ItemClickable.builder(menus.getInt(s + ".slot"))
                            .setItemStack(ItemBuilder.newBuilder(Material.PAPER)
                                    .setName(menus.getString(s + ".name"))
                                    .setLore(menus.getStringList(s + ".lore"))
                                    .build())
                            .setAction(inventoryClickEvent -> {
                                punishmentBuilder.setReason(s + ".punish-reason");
                                return true;
                            })
                            .build());
                }

                break;
            case WARN:

                for (String s : menus.getConfigurationSection("warn-reasons").getKeys(false)) {

                    guiBuilder.addItem(ItemClickable.builder(menus.getInt(s + ".slot"))
                            .setItemStack(ItemBuilder.newBuilder(Material.PAPER)
                                    .setName(menus.getString(s + ".name"))
                                    .setLore(menus.getStringList(s + ".lore"))
                                    .build())
                            .setAction(inventoryClickEvent -> {
                                punishmentBuilder.setReason(s + ".punish-reason");
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
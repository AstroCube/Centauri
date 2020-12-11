package net.astrocube.commons.bukkit.menu;

import net.astrocube.api.core.punishment.PunishmentBuilder;
import net.astrocube.api.core.punishment.PunishmentHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.type.GUIBuilder;
import team.unnamed.gui.core.item.type.ItemBuilder;

import javax.inject.Inject;

public class PunishmentExpirationChooserMenu {

    @Inject private PunishmentHandler punishmentHandler;
    private int expirationInMinutes = -1;

    public Inventory createPunishmentExpirationChooserMenu(Player player, PunishmentBuilder punishmentBuilder) {

        GUIBuilder<ItemClickable> guiBuilder = GUIBuilder.builder("Punishment expiration chooser", 5)
                .addItem(ItemClickable.builder(13)
                        .setItemStack(ItemBuilder.newBuilder(Material.EMERALD_BLOCK)
                                .setName("Confirm punishment")
                                .setLore("Click on this bottom and finish the punishment",
                                        "Remember, if you don't specify the time, the punishment expiration is never",
                                        "But, if you specify there, the punishment will be temporary with the minutes specified.")
                                .build())
                        .setAction(inventoryClickEvent -> {
                            player.closeInventory();
                            if (expirationInMinutes <= -1) {
                                punishmentBuilder
                                        .setDuration(Long.MAX_VALUE)
                                        .build(punishmentHandler, (punishment) -> {
                                        });
                                return true;
                            }

                            punishmentBuilder
                                    .setDuration(expirationInMinutes)
                                    .build(punishmentHandler, (punishment) -> {
                                    });

                            return true;
                        })
                        .build())
                .addItem(ItemClickable.builder(21)
                        .setItemStack(ItemBuilder.newBuilder(Material.SKULL_ITEM)
                                .setName("Increase the time")
                                .setLore("NOTE: This only increase 1 minute to the time value.")
                                .build())
                        .setAction(inventoryClickEvent -> {
                            expirationInMinutes++;
                            System.out.println("Increased 1 minute!");
                            return true;
                        })
                        .build())
                .addItem(ItemClickable.builder(23)
                        .setItemStack(ItemBuilder.newBuilder(Material.SKULL_ITEM)
                                .setName("Decrease the time")
                                .setLore("NOTE: This only decrease 1 minute to the time value.")
                                .build())
                        .setAction(inventoryClickEvent -> {
                            if (expirationInMinutes <= -1) return true;
                            expirationInMinutes--;
                            System.out.println("Decreased 1 minute!");
                            return true;
                        }).build());

        return guiBuilder.build();
    }
}
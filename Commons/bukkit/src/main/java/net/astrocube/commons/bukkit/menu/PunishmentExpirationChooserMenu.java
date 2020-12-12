package net.astrocube.commons.bukkit.menu;

import me.yushust.message.MessageHandler;
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
    @Inject private MessageHandler<Player> messageHandler;
    private int expirationInMinutes = -1;

    public Inventory createPunishmentExpirationChooserMenu(Player player, PunishmentBuilder punishmentBuilder) {

        GUIBuilder<ItemClickable> guiBuilder = GUIBuilder.builder(messageHandler.get(player, "punishment-expiration-menu"), 5)
                .addItem(ItemClickable.builder(13)
                        .setItemStack(ItemBuilder.newBuilder(Material.EMERALD_BLOCK)
                                .setName(messageHandler.get(player, "punishment-expiration-menu.items.confirm.name"))
                                .setLore(messageHandler.getMany(player, "punishment-expiration-menu.items.confirm.lore"))
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
                                .setName(messageHandler.get(player, "punishment-expiration-menu.items.increase.name"))
                                .setLore(messageHandler.getMany(player, "punishment-expiration-menu.items.increase.lore"))
                                .build())
                        .setAction(inventoryClickEvent -> {
                            expirationInMinutes++;
                            System.out.println("Increased 1 minute!");
                            return true;
                        })
                        .build())
                .addItem(ItemClickable.builder(23)
                        .setItemStack(ItemBuilder.newBuilder(Material.SKULL_ITEM)
                                .setName(messageHandler.get(player, "punishment-expiration-menu.items.decrease.name"))
                                .setLore(messageHandler.getMany(player, "punishment-expiration-menu.items.decrease.lore"))
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
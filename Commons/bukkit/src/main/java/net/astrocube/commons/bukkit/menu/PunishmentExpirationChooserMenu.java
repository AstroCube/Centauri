package net.astrocube.commons.bukkit.menu;

import me.yushust.message.MessageHandler;
import net.astrocube.api.core.punishment.PunishmentBuilder;
import net.astrocube.api.core.punishment.PunishmentHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.GUIBuilder;
import team.unnamed.gui.core.item.type.ItemBuilder;

import javax.inject.Inject;

public class PunishmentExpirationChooserMenu {

    @Inject private PunishmentHandler punishmentHandler;
    @Inject private MessageHandler<Player> messageHandler;

    // TODO: 14/12/2020 Implement a minus cache to storage the expiration selected by the users using this menu simultaneous.
    //Only for test
    private int expirationInMinutes = -1;

    public Inventory createPunishmentExpirationChooserMenu(Player player, PunishmentBuilder punishmentBuilder) {

        GUIBuilder guiBuilder = GUIBuilder.builder(messageHandler.get(player, "punishment-expiration-menu.title"), 5)
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
                        .setItemStack(ItemBuilder.newSkullBuilder(Material.SKULL_ITEM)
                                .setName(messageHandler.get(player, "punishment-expiration-menu.items.increase.name"))
                                .setLore(messageHandler.getMany(player, "punishment-expiration-menu.items.increase.lore"))
                                .setUrl("https://textures.minecraft.net/texture/5ff31431d64587ff6ef98c0675810681f8c13bf96f51d9cb07ed7852b2ffd1")
                                .build())
                        .setAction(inventoryClickEvent -> {
                            expirationInMinutes++;
                            return true;
                        })
                        .build())
                .addItem(ItemClickable.builder(23)
                        .setItemStack(ItemBuilder.newSkullBuilder(Material.SKULL_ITEM)
                                .setName(messageHandler.get(player, "punishment-expiration-menu.items.decrease.name"))
                                .setLore(messageHandler.getMany(player, "punishment-expiration-menu.items.decrease.lore"))
                                .setUrl("https://textures.minecraft.net/texture/4e4b8b8d2362c864e062301487d94d3272a6b570afbf80c2c5b148c954579d46")
                                .build())
                        .setAction(inventoryClickEvent -> {
                            if (expirationInMinutes <= -1) return true;
                            expirationInMinutes--;
                            return true;
                        }).build());

        guiBuilder.closeAction(inventoryCloseEvent -> expirationInMinutes = 0);

        return guiBuilder.build();
    }
}
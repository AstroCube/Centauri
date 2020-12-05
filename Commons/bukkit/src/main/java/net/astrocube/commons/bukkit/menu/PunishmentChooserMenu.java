package net.astrocube.commons.bukkit.menu;

import me.yushust.message.MessageHandler;
import net.astrocube.api.core.punishment.PunishmentBuilder;
import net.astrocube.api.core.virtual.punishment.PunishmentDoc;
import net.astrocube.commons.core.punishment.CorePunishmentBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.type.GUIBuilder;
import team.unnamed.gui.core.item.type.ItemBuilder;

public class PunishmentChooserMenu {

    private final MessageHandler<Player> messageHandler;

    public PunishmentChooserMenu(MessageHandler<Player> messageHandler) {
        this.messageHandler = messageHandler;
    }

    public Inventory createPunishmentChooserMenu(Player player, String punished) {

        return GUIBuilder.builder(messageHandler.get(player, "punish-menu.title"), 3)
                .addItem(ItemClickable.builder(11)
                        .setItemStack(ItemBuilder.newBuilder(Material.REDSTONE_BLOCK)
                                .setName(messageHandler.replacing(player, "punish-menu.items.ban.name", punished))
                                .setLore(messageHandler.replacingMany(player, "punish-menu.items.ban.lore", punished))
                                .build())
                        .setAction(inventoryClickEvent -> {
                            PunishmentBuilder punishmentBuilder = CorePunishmentBuilder.newBuilder(inventoryClickEvent.getWhoClicked().getName(), punished, PunishmentDoc.Identity.Type.BAN);
                            player.openInventory(new PunishmentReasonChooserMenu().createPunishmentReasonChooserMenu(player, messageHandler, punishmentBuilder));
                            return true;
                        })
                        .build()
                )
                .addItem(ItemClickable.builder(13)
                        .setItemStack(ItemBuilder.newBuilder(Material.REDSTONE_BLOCK)
                                .setName(messageHandler.replacing(player, "punish-menu.items.kick.name", punished))
                                .setLore(messageHandler.replacingMany(player, "punish-menu.items.kick.lore", punished))
                                .build())
                        .setAction(inventoryClickEvent -> {
                            PunishmentBuilder punishmentBuilder = CorePunishmentBuilder.newBuilder(inventoryClickEvent.getWhoClicked().getName(), punished, PunishmentDoc.Identity.Type.KICK);
                            player.openInventory(new PunishmentReasonChooserMenu().createPunishmentReasonChooserMenu(player, messageHandler, punishmentBuilder));
                            return true;
                        })
                        .build()
                )
                .addItem(ItemClickable.builder(15)
                        .setItemStack(ItemBuilder.newBuilder(Material.REDSTONE_BLOCK)
                                .setName(messageHandler.replacing(player, "punish-menu.items.warn.name", punished))
                                .setLore(messageHandler.replacingMany(player, "punish-menu.items.warn.lore", punished))
                                .build())
                        .setAction(inventoryClickEvent -> {
                            PunishmentBuilder punishmentBuilder = CorePunishmentBuilder.newBuilder(inventoryClickEvent.getWhoClicked().getName(), punished, PunishmentDoc.Identity.Type.WARN);
                            player.openInventory(new PunishmentReasonChooserMenu().createPunishmentReasonChooserMenu(player, messageHandler, punishmentBuilder));
                            return true;
                        })
                        .build()
                )
                .build();
    }
}
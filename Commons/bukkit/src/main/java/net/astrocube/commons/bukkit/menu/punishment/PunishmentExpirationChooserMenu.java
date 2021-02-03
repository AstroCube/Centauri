package net.astrocube.commons.bukkit.menu.punishment;

import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.util.TimeParser;
import net.astrocube.api.core.punishment.PunishmentBuilder;
import net.astrocube.api.core.punishment.PunishmentHandler;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.GUIBuilder;
import team.unnamed.gui.core.item.type.ItemBuilder;

import javax.inject.Inject;

public class PunishmentExpirationChooserMenu {

    private @Inject PunishmentHandler punishmentHandler;
    private @Inject MessageHandler messageHandler;
    private @Inject Plugin plugin;

    public Inventory createPunishmentExpirationChooserMenu(Player player, PunishmentBuilder punishmentBuilder) {

        GUIBuilder guiBuilder = GUIBuilder.builder(messageHandler.get(player, "punishment-expiration-menu.title"), 5)
                .addItem(ItemClickable.builder(13)
                        .setItemStack(ItemBuilder.newBuilder(Material.EMERALD_BLOCK)
                                .setName(messageHandler.get(player, "punishment-expiration-menu.items.confirm.name"))
                                .setLore(messageHandler.getMany(player, "punishment-expiration-menu.items.confirm.lore"))
                                .build())
                        .setAction(inventoryClickEvent -> {
                            player.closeInventory();

                            new AnvilGUI.Builder()
                                    .title(messageHandler.get(player, "punishment-expiration.title"))
                                    .text(messageHandler.get(player, "punishment-expiration.holder"))
                                    .onComplete((playerIgnored, text) -> {
                                        long punishmentExpiration = TimeParser.parseStringDuration(text);

                                        if (punishmentExpiration == -1) {
                                            return AnvilGUI.Response.text(messageHandler.get(player, "punishment-expiration.invalid-number"));
                                        }

                                        punishmentBuilder.setDuration(punishmentExpiration)
                                                .build(punishmentHandler, (ignoredPunishment) -> {});

                                        return AnvilGUI.Response.close();
                                    })
                                    .plugin(plugin)
                                    .open(player);
                            return true;
                        })
                        .build());

        return guiBuilder.build();
    }
}
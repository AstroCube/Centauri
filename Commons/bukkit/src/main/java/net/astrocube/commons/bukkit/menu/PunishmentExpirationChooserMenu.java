package net.astrocube.commons.bukkit.menu;

import me.yushust.message.MessageHandler;
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

    @Inject
    private PunishmentHandler punishmentHandler;
    @Inject
    private MessageHandler<Player> messageHandler;
    @Inject
    private Plugin plugin;

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
                                    .text(messageHandler.get(player, "punishment-expiration.title"))

                                    .onComplete((playerIgnored, text) -> {
                                        long punishmentExpiration;
                                        try {
                                            punishmentExpiration = Long.parseLong(text);
                                        } catch (NumberFormatException ignored) {
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
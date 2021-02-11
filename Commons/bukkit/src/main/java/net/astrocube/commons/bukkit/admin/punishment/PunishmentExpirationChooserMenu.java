package net.astrocube.commons.bukkit.admin.punishment;

import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.bukkit.util.TimeParser;
import net.astrocube.api.core.punishment.PunishmentBuilder;
import net.astrocube.api.core.punishment.PunishmentHandler;
import net.astrocube.api.core.virtual.punishment.PunishmentDoc;
import net.astrocube.commons.bukkit.menu.GenericHeadHelper;
import net.astrocube.commons.bukkit.menu.MenuUtils;
import net.astrocube.commons.core.utils.PrettyTimeUtils;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.GUIBuilder;
import team.unnamed.gui.core.item.type.ItemBuilder;

import javax.inject.Inject;

public class PunishmentExpirationChooserMenu {

    private @Inject PunishmentHandler punishmentHandler;
    private @Inject GenericHeadHelper genericHeadHelper;
    private @Inject MessageHandler messageHandler;
    private @Inject PunishmentReasonChooserMenu punishmentReasonChooserMenu;
    private @Inject Plugin plugin;

    public Inventory createPunishmentExpirationChooserMenu(Player player, PunishmentBuilder punishmentBuilder, String criteria, boolean search) {

        GUIBuilder guiBuilder = GUIBuilder.builder(messageHandler.get(player, "punishment-expiration-menu.title"), 6)
                .addItem(
                        genericHeadHelper.generateDecorator(
                                genericHeadHelper.generateSkull(
                                        player,
                                        punishmentBuilder.getTarget(),
                                        messageHandler.replacingMany(
                                                player, "punishment-expiration-menu.items.actual.lore",
                                                "%%reason%%", punishmentBuilder.getReason(),
                                                "%%type%%", messageHandler.get(player, "punish-menu.type." + punishmentBuilder.getType().toString().toLowerCase()),
                                                "%%expires%%", punishmentBuilder.getDuration() == -1 ? messageHandler.get(player, "punishment-expiration.never") :
                                                        PrettyTimeUtils.getHumanDate(
                                                                PunishmentHandler.generateFromExpiration(
                                                                        punishmentBuilder.getDuration()
                                                                ).toDate(),
                                                                punishmentBuilder.getIssuer().getLanguage())
                                        )
                                ),
                                22
                        )
                )
                .addItem(ItemClickable.builder(24)
                        .setItemStack(ItemBuilder.newBuilder(Material.EMERALD_BLOCK)
                                .setName(messageHandler.get(player, "punishment-expiration-menu.items.confirm.name"))
                                .setLore(messageHandler.getMany(player, "punishment-expiration-menu.items.confirm.lore"))
                                .build())
                        .setAction(inventoryClickEvent -> {
                            punishmentBuilder.build(
                                    punishmentHandler,
                                    (punishment, error) -> {
                                        if (error != null) {
                                            messageHandler.sendIn(player, AlertModes.ERROR, "punish.error");
                                        }
                                        player.closeInventory();
                                    });
                            return true;
                        })
                        .build()
                );

        MenuUtils.generateFrame(guiBuilder);

        if (punishmentBuilder.getType() == PunishmentDoc.Identity.Type.BAN) {
            guiBuilder.addItem(ItemClickable.builder(20)
                    .setItemStack(ItemBuilder.newBuilder(Material.WORKBENCH)
                            .setName(messageHandler.get(player, "punishment-expiration-menu.items.edit.name"))
                            .setLore(
                                    messageHandler.replacingMany(
                                            player, "punishment-expiration-menu.items.edit.lore",
                                            "%%duration%%",
                                            punishmentBuilder.getDuration() == -1 ?
                                                    messageHandler.get(player, "punishment-expiration.never") :
                                                    PrettyTimeUtils.getHumanDate(
                                                            PunishmentHandler.generateFromExpiration(
                                                                    punishmentBuilder.getDuration()
                                                            ).toDate(),
                                                            punishmentBuilder.getIssuer().getLanguage()
                                                    )
                                    )
                            )
                            .build())
                    .setAction(inventoryClickEvent -> {
                        player.closeInventory();
                        new AnvilGUI.Builder()
                                .title(messageHandler.get(player, "punishment-expiration.title"))
                                .onClose(p -> player.openInventory(createPunishmentExpirationChooserMenu(player, punishmentBuilder, criteria, search)))
                                .text(messageHandler.get(player, "punishment-expiration.holder"))
                                .onComplete((playerIgnored, text) -> {
                                    long punishmentExpiration = TimeParser.parseStringDuration(text);

                                    if (punishmentExpiration == -1) {
                                        return AnvilGUI.Response.text(messageHandler.get(player, "punishment-expiration.invalid-number"));
                                    }

                                    punishmentBuilder.setDuration(punishmentExpiration);
                                    createPunishmentExpirationChooserMenu(player, punishmentBuilder, criteria, search);
                                    return AnvilGUI.Response.close();
                                })
                                .plugin(plugin)
                                .open(player);
                        return true;
                    }).build()
            );
        }

        guiBuilder.addItem(genericHeadHelper.generateDefaultClickable(
                genericHeadHelper.backButton(player),
                45,
                ClickType.LEFT,
                p -> p.openInventory(search ?
                        punishmentReasonChooserMenu.createSearch(player, punishmentBuilder, criteria, 1) :
                        punishmentReasonChooserMenu.createFilter(player, punishmentBuilder, 1))
        ));

        return guiBuilder.build();
    }

}
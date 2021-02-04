package net.astrocube.commons.bukkit.menu.punishment.helper;

import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.punishment.PresetPunishment;
import net.astrocube.api.core.punishment.PunishmentBuilder;
import net.astrocube.api.core.punishment.PunishmentHandler;
import net.astrocube.commons.bukkit.menu.GenericHeadHelper;
import net.astrocube.commons.bukkit.menu.HeadLibrary;
import net.astrocube.commons.bukkit.menu.MenuUtils;
import net.astrocube.commons.bukkit.menu.punishment.PunishmentChooserMenu;
import net.astrocube.commons.bukkit.menu.punishment.PunishmentExpirationChooserMenu;
import net.astrocube.commons.bukkit.menu.punishment.PunishmentReasonChooserMenu;
import net.astrocube.commons.core.utils.Pagination;
import net.astrocube.commons.core.utils.PrettyTimeUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.item.type.ItemBuilder;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class PunishmentReasonChooserHelper {

    private @Inject PunishmentExpirationChooserMenu punishmentExpirationChooserMenu;
    private @Inject PunishmentReasonChooserMenu punishmentReasonChooserMenu;
    private @Inject PunishmentChooserMenu punishmentChooserMenu;
    private @Inject MessageHandler messageHandler;
    private @Inject GenericHeadHelper genericHeadHelper;

    public List<ItemClickable> buildPunishReasons
            (Player player, PunishmentBuilder punishmentBuilder, Pagination<PresetPunishment> punishments, String criteria, boolean search, int page) {

        List<ItemClickable> clickableArray = new ArrayList<>();

        int index = 10;
        for (PresetPunishment punishment: punishments.getPage(page)) {

            while (MenuUtils.isMarkedSlot(index)) {
                index++;
            }

            String translated = messageHandler.get(player, "punish-menu.reasons." + punishment.getId() + ".reason");

            clickableArray.add(
                    genericHeadHelper.generateDefaultClickable(
                            ItemBuilder.newBuilder(Material.PAPER)
                                    .setName(ChatColor.AQUA +
                                            messageHandler.get(player, "punish-menu.reasons." + punishment.getId() + ".title")  +
                                            ChatColor.GRAY + (search ? " - " + messageHandler.get(player, "punish-menu.type." + punishment.getType().toString().toLowerCase()) : "")
                                    )
                                    .setLore(
                                            messageHandler.replacingMany(
                                                    player, "punish-menu.lore",
                                                    "%%reason%%", translated,
                                                    "%%expire%%", punishment.getExpiration() == -1 ? messageHandler.get(player, "punishment-expiration.never") :
                                                            PrettyTimeUtils.getHumanDate(
                                                                    PunishmentHandler.generateFromExpiration(
                                                                            punishment.getExpiration()
                                                                    ).toDate(),
                                                                    punishmentBuilder.getIssuer().getLanguage()
                                                    )
                                            )
                                    )
                                    .build(),
                            index,
                            ClickType.LEFT,
                            (p) -> {
                                if (search) {
                                    punishmentBuilder.setType(punishment.getType());
                                }
                                punishmentBuilder.setReason(translated);
                                punishmentBuilder.setDuration(punishment.getExpiration());
                                player.openInventory(punishmentExpirationChooserMenu.createPunishmentExpirationChooserMenu(player, punishmentBuilder, criteria, search));
                            }
                    )
            );

        }

        clickableArray.add(
                genericHeadHelper.generateDefaultClickable(
                        genericHeadHelper.backButton(player),
                        45,
                        ClickType.LEFT,
                        p -> p.openInventory(punishmentChooserMenu.createPunishmentChooserMenu(
                                player,
                                punishmentBuilder.getIssuer(),
                                punishmentBuilder.getTarget()
                        ))
                )
        );

        if (punishments.getObjects().isEmpty()) {
            clickableArray.add(
                    genericHeadHelper.generateDecorator(
                            genericHeadHelper.getEmptyHead(player, "punishment-expiration-menu.items.empty"),
                            22
                    )
            );
        }

        if (punishments.pageExists(page - 1) && (page - 1) != 0) {
            clickableArray.add(
                    genericHeadHelper.generateDefaultClickable(
                            genericHeadHelper.getPreviousHead(player, page),
                            48,
                            ClickType.LEFT,
                            (p) -> switchPagination(player, punishmentBuilder, (page - 1), criteria, search)
                    )
            );
        }

        clickableArray.add(
                genericHeadHelper.generateDecorator(genericHeadHelper.getActual(player, page), 49)
        );

        if (punishments.pageExists(page + 1)) {
            clickableArray.add(
                    genericHeadHelper.generateDefaultClickable(
                            genericHeadHelper.getNextHead(player, page),
                            50,
                            ClickType.LEFT,
                            (p) -> switchPagination(player, punishmentBuilder, (page + 1), criteria, search)
                    )
            );
        }

        return clickableArray;
    }

    private void switchPagination(Player player, PunishmentBuilder builder, int page, String criteria, boolean search) {
        player.openInventory(search ?
                punishmentReasonChooserMenu.createSearch(player, builder, criteria, page) :
                punishmentReasonChooserMenu.createFilter(player, builder, page)
        );
    }

}
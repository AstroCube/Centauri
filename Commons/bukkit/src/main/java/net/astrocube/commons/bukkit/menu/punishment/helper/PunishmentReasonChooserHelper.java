package net.astrocube.commons.bukkit.menu.punishment.helper;

import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.punishment.PresetPunishment;
import net.astrocube.api.core.punishment.PunishmentBuilder;
import net.astrocube.commons.bukkit.menu.HeadLibrary;
import net.astrocube.commons.bukkit.menu.MenuUtils;
import net.astrocube.commons.bukkit.menu.punishment.PunishmentExpirationChooserMenu;
import net.astrocube.commons.bukkit.menu.punishment.PunishmentReasonChooserMenu;
import net.astrocube.commons.core.utils.Pagination;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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
    private @Inject MessageHandler messageHandler;

    public List<ItemClickable> buildPunishReasons
            (Player player, PunishmentBuilder punishmentBuilder, Pagination<PresetPunishment> punishments, int page) {

        List<ItemClickable> clickableArray = new ArrayList<>();

        int index = 10;
        for (PresetPunishment punishment: punishments.getPage(page)) {

            while (MenuUtils.isMarkedSlot(index)) {
                index++;
            }

            ItemClickable itemClickable = ItemClickable.builder(index)
                    .setItemStack(ItemBuilder.newBuilder(Material.PAPER)
                            .setName(ChatColor.AQUA + messageHandler.get(player, "punish-menu.reasons." + punishment.getId() + ".title"))
                            .setLore(messageHandler.get(player, "punish-menu.reasons." + punishment.getId() + ".lore"))
                            .build())
                    .setAction(event -> {
                        punishmentBuilder.setReason(messageHandler.get(player, "punish-menu.reasons." + punishment.getId() + ".reason"));
                        player.openInventory(punishmentExpirationChooserMenu.createPunishmentExpirationChooserMenu(player, punishmentBuilder));
                        return true;
                    })
                    .build();

            clickableArray.add(itemClickable);

        }

        if (punishments.getObjects().isEmpty()) {
            clickableArray.add(
                    ItemClickable.builder(22)
                        .setItemStack(
                                ItemBuilder.newBuilder(Material.REDSTONE_BLOCK)
                                        .setName(messageHandler.get(player, "punishment-expiration-menu.items.empty"))
                                        .build()
                        )
                    .build()
            );
        }

        if (punishments.pageExists(page - 1) && (page - 1) != 0) {
            clickableArray.add(
                    ItemClickable.builder(48)
                            .setItemStack(getStack(player, HeadLibrary.LEFT_ARROW_PC, "pagination.previous",page - 1))
                            .setAction(event -> switchPagination(player, punishmentBuilder, (page - 1)))
                            .build()
            );
        }

        clickableArray.add(
                ItemClickable.builder(49)
                        .setItemStack(getStack(player, HeadLibrary.BOOK_LEFT, "pagination.actual", page))
                        .build()
        );

        if (punishments.pageExists(page + 1)) {
            clickableArray.add(ItemClickable.builder(50)
                    .setItemStack(getStack(player, HeadLibrary.RIGHT_ARROW_PC, "pagination.next", page + 1))
                    .setAction(event -> switchPagination(player, punishmentBuilder, (page + 1)))
                    .build()
            );
        }

        return clickableArray;
    }

    private ItemStack getStack(Player player, HeadLibrary head, String translation, int page) {
        ItemStack previousStack = MenuUtils.generateHead(head);
        ItemMeta meta = previousStack.getItemMeta();
        meta.setDisplayName(
                messageHandler.replacing(
                        player, translation,
                        "%%page%%", page + ""
                )
        );
        previousStack.setItemMeta(meta);
        return previousStack;
    }

    private boolean switchPagination(Player player, PunishmentBuilder builder, int page) {
        player.openInventory(punishmentReasonChooserMenu.createPunishmentReasonChooserMenu(player, builder, page));
        return true;
    }

}
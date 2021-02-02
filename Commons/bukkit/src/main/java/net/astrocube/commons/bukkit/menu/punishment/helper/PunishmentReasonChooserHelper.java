package net.astrocube.commons.bukkit.menu.punishment.helper;

import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.punishment.PresetPunishment;
import net.astrocube.api.bukkit.punishment.PresetPunishmentCache;
import net.astrocube.api.core.punishment.PunishmentBuilder;
import net.astrocube.commons.bukkit.menu.HeadLibrary;
import net.astrocube.commons.bukkit.menu.MenuUtils;
import net.astrocube.commons.bukkit.menu.punishment.PunishmentExpirationChooserMenu;
import net.astrocube.commons.core.utils.Pagination;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.item.type.ItemBuilder;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PunishmentReasonChooserHelper {

    private @Inject PunishmentExpirationChooserMenu punishmentExpirationChooserMenu;
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

        return clickableArray;
    }

    private ItemStack getPreviousPage(Player player, int page) {
        ItemStack previousStack = MenuUtils.generateHead(HeadLibrary.LEFT_ARROW_PC);
        ItemMeta meta = previousStack.getItemMeta();
        meta.setDisplayName(
                messageHandler.replacing(
                        player, "pagination.previous",
                        "%%page%%", page + ""
                )
        );
        previousStack.setItemMeta(meta);
        return previousStack;
    }

}
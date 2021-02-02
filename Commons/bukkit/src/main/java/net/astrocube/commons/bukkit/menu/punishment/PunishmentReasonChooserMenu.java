package net.astrocube.commons.bukkit.menu.punishment;

import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.punishment.PresetPunishment;
import net.astrocube.api.bukkit.punishment.PresetPunishmentCache;
import net.astrocube.api.core.punishment.PunishmentBuilder;
import net.astrocube.commons.bukkit.menu.MenuUtils;
import net.astrocube.commons.bukkit.menu.punishment.helper.PunishmentReasonChooserHelper;
import net.astrocube.commons.core.utils.Pagination;
import net.astrocube.commons.core.utils.SimplePagination;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.GUIBuilder;

import javax.inject.Inject;

public class PunishmentReasonChooserMenu {

    private @Inject PunishmentReasonChooserHelper punishmentReasonChooserHelper;
    private @Inject PresetPunishmentCache presetPunishmentCache;
    private @Inject MessageHandler messageHandler;

    public Inventory createPunishmentReasonChooserMenu(Player player, PunishmentBuilder punishmentBuilder, int page) {

        GUIBuilder guiBuilder = GUIBuilder
                .builder(messageHandler.get(player, "punishment-expiration-menu.title"), 6);


        Pagination<PresetPunishment> presetPunishment =
                new SimplePagination<>(28, presetPunishmentCache.getPunishments(punishmentBuilder.getType()));

        for (int i = 0; i < 54; i++) {
            if (MenuUtils.isMarkedSlot(i)) {
                guiBuilder.addItem(ItemClickable.builder(i).setItemStack(MenuUtils.generateStainedPane()).build());
            }
        }

        punishmentReasonChooserHelper.buildPunishReasons(player, punishmentBuilder, presetPunishment, page)
                .forEach(guiBuilder::addItem);

        return guiBuilder.build();
    }
}
package net.astrocube.commons.bukkit.menu.punishment;

import me.yushust.message.MessageHandler;
import net.astrocube.api.core.punishment.PunishmentBuilder;
import net.astrocube.commons.bukkit.menu.MenuUtils;
import net.astrocube.commons.bukkit.menu.punishment.helper.PunishmentReasonChooserHelper;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.GUIBuilder;

import javax.inject.Inject;

public class PunishmentReasonChooserMenu {

    private @Inject PunishmentReasonChooserHelper punishmentReasonChooserHelper;
    private @Inject MessageHandler messageHandler;

    public Inventory createPunishmentReasonChooserMenu(Player player,
                                                       PunishmentBuilder punishmentBuilder) {
        GUIBuilder guiBuilder = GUIBuilder
                .builder(messageHandler.get(player, "punishment-expiration-menu.title"), 6);

        for (int i = 0; i < 54; i++) {
            if (MenuUtils.isMarkedSlot(i)) {
                guiBuilder.addItem(ItemClickable.builder(i).setItemStack(MenuUtils.generateStainedPane()).build());
            }
        }

        punishmentReasonChooserHelper.buildPunishReasons(player, punishmentBuilder)
                .forEach(guiBuilder::addItem);

        return guiBuilder.build();
    }
}
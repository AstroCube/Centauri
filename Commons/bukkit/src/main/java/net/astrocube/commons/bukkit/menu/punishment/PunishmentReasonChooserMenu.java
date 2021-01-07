package net.astrocube.commons.bukkit.menu.punishment;

import net.astrocube.api.core.punishment.PunishmentBuilder;
import net.astrocube.commons.bukkit.menu.punishment.helper.PunishmentReasonChooserHelper;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import team.unnamed.gui.core.gui.GUIBuilder;

import javax.inject.Inject;

public class PunishmentReasonChooserMenu {

    @Inject private Plugin plugin;
    @Inject private PunishmentReasonChooserHelper punishmentReasonChooserHelper;

    public Inventory createPunishmentReasonChooserMenu(Player player,
                                                       PunishmentBuilder punishmentBuilder) {
        return GUIBuilder
                .builder(ChatColor
                        .translateAlternateColorCodes('&', plugin.getConfig().getString("punishment-reason-menu.title")), 3)
                .setItems(punishmentReasonChooserHelper.buildPunishReasons(player, punishmentBuilder))
                .build();
    }
}
package net.astrocube.commons.bukkit.menu.punishment;

import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.punishment.PresetPunishment;
import net.astrocube.api.bukkit.punishment.PresetPunishmentCache;
import net.astrocube.api.core.punishment.PunishmentBuilder;
import net.astrocube.commons.bukkit.menu.MenuUtils;
import net.astrocube.commons.bukkit.menu.punishment.helper.PunishmentReasonChooserHelper;
import net.astrocube.commons.core.utils.Pagination;
import net.astrocube.commons.core.utils.SimplePagination;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import team.unnamed.gui.core.gui.GUIBuilder;

import javax.inject.Inject;
import java.util.stream.Collectors;

public class PunishmentReasonChooserMenu {

    private @Inject PunishmentReasonChooserHelper punishmentReasonChooserHelper;
    private @Inject PresetPunishmentCache presetPunishmentCache;
    private @Inject MessageHandler messageHandler;

    public Inventory createFilter(Player player, PunishmentBuilder builder, int page) {

        Pagination<PresetPunishment> presetPunishment =
                new SimplePagination<>(28, presetPunishmentCache.getPunishments(builder.getType()));

        return createChooser(player, builder, presetPunishment, "", false, page);
    }

    public Inventory createSearch(Player player, PunishmentBuilder builder, String search, int page) {

        Pagination<PresetPunishment> presetPunishment =
                new SimplePagination<>(
                        28,
                        presetPunishmentCache.getPunishments()
                                .stream()
                                .filter(p ->
                                        messageHandler.get(
                                                player,
                                                "punish-menu.reasons." + p.getId() + ".title"
                                        ).contains(search))
                                .collect(Collectors.toSet())
                );


        return createChooser(player, builder, presetPunishment, search, true, page);
    }

    private Inventory createChooser(Player player, PunishmentBuilder builder, Pagination<PresetPunishment> preset, String criteria, boolean search, int page) {

        GUIBuilder guiBuilder = GUIBuilder
                .builder(messageHandler.get(player, "punishment-expiration-menu.title"), 6);
        MenuUtils.generateFrame(guiBuilder);
        punishmentReasonChooserHelper.buildPunishReasons(player, builder, preset, criteria, search, page)
                .forEach(guiBuilder::addItem);

        return guiBuilder.build();
    }

}
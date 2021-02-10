package net.astrocube.commons.bukkit.menu.punishment;

import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.menu.ShapedMenuGenerator;
import net.astrocube.api.bukkit.punishment.PresetPunishment;
import net.astrocube.api.bukkit.punishment.PresetPunishmentCache;
import net.astrocube.api.core.punishment.PunishmentBuilder;
import net.astrocube.commons.bukkit.menu.punishment.helper.PunishClickableGenerator;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import javax.inject.Inject;
import java.util.Set;
import java.util.stream.Collectors;

public class PunishmentReasonChooserMenu {

    private @Inject PunishClickableGenerator punishClickableGenerator;
    private @Inject PresetPunishmentCache presetPunishmentCache;
    private @Inject PunishmentChooserMenu punishmentChooserMenu;
    private @Inject MessageHandler messageHandler;
    private @Inject ShapedMenuGenerator shapedMenuGenerator;

    public Inventory createFilter(Player player, PunishmentBuilder builder, int page) {
        return createChooser(
                player,
                builder,
                presetPunishmentCache.getPunishments(builder.getType()),
                "",
                false,
                page
        );
    }

    public Inventory createSearch(Player player, PunishmentBuilder builder, String search, int page) {
        return createChooser(
                player,
                builder,
                presetPunishmentCache.getPunishments()
                        .stream()
                        .filter(p ->
                                messageHandler.get(
                                        player,
                                        "punish-menu.reasons." + p.getId() + ".title"
                                ).contains(search))
                        .collect(Collectors.toSet()),
                search,
                true,
                page
        );
    }

    private Inventory createChooser(Player player, PunishmentBuilder builder, Set<PresetPunishment> preset, String criteria, boolean search, int page) {
        return shapedMenuGenerator.generate(
                player,
                messageHandler.get(player, "punishment-expiration-menu.title"),
                p -> p.openInventory(punishmentChooserMenu.createPunishmentChooserMenu(
                        player,
                        builder.getIssuer(),
                        builder.getTarget()
                )),
                punishClickableGenerator.buildPunishReasons(player, builder, preset, criteria, search)
        );
    }

}
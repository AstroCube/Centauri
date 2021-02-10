package net.astrocube.commons.bukkit.punishment.lookup;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.punishment.PresetPunishment;
import net.astrocube.api.bukkit.punishment.lookup.PunishmentIconGenerator;
import net.astrocube.api.bukkit.punishment.lookup.PunishmentLookupMenu;
import net.astrocube.api.core.virtual.punishment.Punishment;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.bukkit.menu.GenericHeadHelper;
import net.astrocube.commons.bukkit.menu.MenuUtils;
import net.astrocube.commons.core.utils.Pagination;
import net.astrocube.commons.core.utils.SimplePagination;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import team.unnamed.gui.core.gui.GUIBuilder;

import java.util.Set;

@Singleton
public class CorePunishmentLookupMenu implements PunishmentLookupMenu {

    private @Inject MessageHandler messageHandler;
    private @Inject PunishmentIconGenerator punishmentIconGenerator;
    private @Inject GenericHeadHelper genericHeadHelper;

    @Override
    public void generateMenu(Set<Punishment> punishments, User issuer, User target, Player player, int page) {

        Pagination<Punishment> pagination = new SimplePagination<>(28, punishments);

        GUIBuilder builder = GUIBuilder.builder(
                messageHandler.replacing(
                        player, "punish-menu.lookup.menu",
                        "%%player%%", target.getDisplay()
                )
        );

        int index = 10;
        for (Punishment punishment: pagination.getPage(page)) {

            while (MenuUtils.isMarkedSlot(index)) {
                index++;
            }

            builder.addItem(
                    genericHeadHelper.generateDecorator(
                            punishmentIconGenerator.generateFromPunishment(punishment, player, issuer),
                            index
                    )
            );
        }

        MenuUtils.generateFrame(builder);

        if (pagination.pageExists(page - 1) && (page - 1) != 0) {
            builder.addItem(
                    genericHeadHelper.generateDefaultClickable(
                            genericHeadHelper.getPreviousHead(player, page),
                            49,
                            ClickType.LEFT,
                            clicker -> generateMenu(punishments, issuer, target, player, page - 1)
                    )
            );
        }

        builder.addItem(
                genericHeadHelper.generateDecorator(genericHeadHelper.getActual(player, page), 49)
        );

        if (pagination.pageExists(page + 1)) {
            builder.addItem(
                    genericHeadHelper.generateDefaultClickable(
                            genericHeadHelper.getNextHead(player, page),
                            50,
                            ClickType.LEFT,
                            (p) -> generateMenu(punishments, issuer, target, player, page + 1)
                    )
            );
        }

        player.openInventory(builder.build());

    }

}

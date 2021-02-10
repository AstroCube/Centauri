package net.astrocube.commons.bukkit.punishment.lookup;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.menu.ShapedMenuGenerator;
import net.astrocube.api.bukkit.punishment.lookup.PunishmentIconGenerator;
import net.astrocube.api.bukkit.punishment.lookup.PunishmentLookupMenu;
import net.astrocube.api.core.virtual.punishment.Punishment;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class CorePunishmentLookupMenu implements PunishmentLookupMenu {

    private @Inject MessageHandler messageHandler;
    private @Inject PunishmentIconGenerator punishmentIconGenerator;
    private @Inject ShapedMenuGenerator shapedMenuGenerator;

    @Override
    public void generateMenu(Set<Punishment> punishments, User issuer, User target, Player player, int page) {
        player.openInventory(shapedMenuGenerator.generate(
                player,
                messageHandler.replacing(
                        player, "punish-menu.lookup.menu",
                        "%%player%%", target.getDisplay()
                ),
                (p) -> {},
                null,
                punishments.stream()
                        .map(p -> punishmentIconGenerator.generateFromPunishment(p, player, issuer))
                        .collect(Collectors.toSet())
        ));
    }

}

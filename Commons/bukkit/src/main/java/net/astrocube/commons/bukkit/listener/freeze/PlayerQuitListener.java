package net.astrocube.commons.bukkit.listener.freeze;

import net.astrocube.api.core.punishment.PunishmentBuilder;
import net.astrocube.api.core.punishment.PunishmentHandler;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.punishment.PunishmentDoc;
import net.astrocube.commons.core.punishment.CorePunishmentBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.inject.Inject;

public class PlayerQuitListener implements Listener {

    @Inject
    private PunishmentHandler punishmentHandler;

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        if (!player.hasMetadata("freeze")) {
            return;
        }

        /*
        CorePunishmentBuilder.newBuilder(
                "server",
                player,
                PunishmentDoc.Identity.Type.BAN)
                .setDuration(Long.MAX_VALUE)
                .setReason("Disconnection while was freezed.")
                .build(punishmentHandler, (punishmentIgnored) -> {});
         */
    }
}
package net.astrocube.commons.bukkit.listener.freeze;

import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.punishment.freeze.FrozenUserProvider;
import net.astrocube.api.core.punishment.PunishmentHandler;
import net.astrocube.api.core.virtual.punishment.PunishmentDoc;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.inject.Inject;

public class PlayerQuitListener implements Listener {

    private @Inject FrozenUserProvider frozenUserProvider;
    private @Inject MessageHandler messageHandler;
    private @Inject PunishmentHandler punishmentHandler;

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        if (frozenUserProvider.isFrozen(event.getPlayer())) {

            frozenUserProvider.unFreeze(event.getPlayer());

            punishmentHandler.createPunishment(
                    "",
                    event.getPlayer().getDatabaseIdentifier(),
                    messageHandler.get(event.getPlayer(), "punish.freeze.disconnect"),
                    PunishmentDoc.Identity.Type.BAN,
                    -1,
                    true,
                    false,
                    (punishment, e) -> {}
            );

        }

    }
}
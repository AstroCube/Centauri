package net.astrocube.commons.bukkit.punishment;

import com.google.inject.Inject;

import net.astrocube.api.bukkit.punishment.PunishmentAnnouncer;
import net.astrocube.api.bukkit.punishment.PunishmentHelper;
import net.astrocube.api.core.punishment.PunishmentHandler;
import net.astrocube.api.core.virtual.punishment.PunishmentDoc;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class CorePunishmentHelper implements PunishmentHelper {

    private @Inject PunishmentAnnouncer punishmentAnnouncer;
    private @Inject PunishmentHandler punishmentHandler;

    @Override
    public void createBan(Player issuer, OfflinePlayer punished, String duration, String reason) {

    }

    @Override
    public void createWarn(Player issuer, OfflinePlayer punished, String reason) {
        punishmentHandler.createPunishment(
                issuer.getDatabaseIdentifier(),
                issuer.getDatabaseIdentifier(), //change to punished id.
                reason,
                PunishmentDoc.Identity.Type.WARN,
                -1,
                false,
                false,
                callback -> punishmentAnnouncer.alertWarn(issuer, punished.isOnline() ? (Player) punished : null, reason)
        );
    }

}
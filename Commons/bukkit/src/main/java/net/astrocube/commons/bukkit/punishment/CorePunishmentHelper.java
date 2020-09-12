package net.astrocube.commons.bukkit.punishment;

import com.google.inject.Inject;

import net.astrocube.api.bukkit.punishment.PunishmentAnnouncer;
import net.astrocube.api.bukkit.punishment.PunishmentHelper;
import net.astrocube.api.bukkit.user.UserMatcher;
import net.astrocube.api.core.concurrent.Callback;
import net.astrocube.api.core.punishment.PunishmentHandler;
import net.astrocube.api.core.virtual.punishment.Punishment;
import net.astrocube.api.core.virtual.punishment.PunishmentDoc;

import net.astrocube.commons.bukkit.utils.TimeUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class CorePunishmentHelper implements PunishmentHelper {

    private @Inject PunishmentAnnouncer punishmentAnnouncer;
    private @Inject PunishmentHandler punishmentHandler;
    private @Inject UserMatcher userMatcher;

    @Override
    public void createBan(Player issuer, OfflinePlayer punished, String duration, String reason) {
        createPunish(
                issuer,
                punished,
                duration,
                reason,
                PunishmentDoc.Identity.Type.BAN,
                callback -> punishmentAnnouncer.alertBan(issuer, punished.isOnline() ? (Player) punished : null, reason, duration)
        );
    }

    @Override
    public void createWarn(Player issuer, OfflinePlayer punished, String reason) {
        createPunish(
                issuer,
                punished,
                null,
                reason,
                PunishmentDoc.Identity.Type.KICK,
                callback -> punishmentAnnouncer.alertWarn(issuer, punished.isOnline() ? (Player) punished : null, reason)
        );
    }

    @Override
    public void createKick(Player issuer, OfflinePlayer punished, String reason) {
        createPunish(
                issuer,
                punished,
                null,
                reason,
                PunishmentDoc.Identity.Type.KICK,
                callback -> punishmentAnnouncer.alertKick(issuer, punished.isOnline() ? (Player) punished : null, reason)
        );
    }

    private void createPunish(Player issuer,
                              OfflinePlayer punished,
                              String duration,
                              String reason,
                              PunishmentDoc.Identity.Type type,
                              Callback<Punishment> callback) {

        punishmentHandler.createPunishment(
                issuer.getDatabaseIdentifier(),
                userMatcher.findUserByName(punished.getName()).getId(),
                reason,
                type,
                duration == null || duration.equalsIgnoreCase("permanent") ? -1 : TimeUtils.parseDurationToLong(duration),
                false,
                false,
                callback
        );
    }

}
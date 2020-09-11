package net.astrocube.api.bukkit.punishment;

import org.bukkit.entity.Player;

public interface PunishmentAnnouncer {

    void alertWarn(Player issuer, Player punished, String reason);

    void alertBan(Player issuer, Player punished, String reason, String duration);

    void alertKick(Player issuer, Player punished, String reason);

}
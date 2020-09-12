package net.astrocube.api.bukkit.punishment;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public interface PunishmentHelper {

    void createBan(Player issuer, OfflinePlayer punished, String duration, String reason);

    void createWarn(Player issuer, OfflinePlayer punished, String reason);

    void createKick(Player issuer, OfflinePlayer punished, String reason);

}
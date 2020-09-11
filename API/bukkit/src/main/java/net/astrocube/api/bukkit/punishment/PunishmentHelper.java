package net.astrocube.api.bukkit.punishment;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public interface PunishmentHelper {

    void createBan(Player issuer, OfflinePlayer punished, String duration, String reason) throws Exception;

    void createWarn(Player issuer, OfflinePlayer punished, String reason);

}
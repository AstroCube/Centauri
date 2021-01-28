package net.astrocube.commons.bukkit.utils;

import me.yushust.message.MessageHandler;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class UserUtils {

    public static boolean checkSamePlayer(Player player, OfflinePlayer target, MessageHandler messageHandler) {

        if (!player.getUniqueId().equals(target.getUniqueId())) {
            return false;
        }

        ChatAlertLibrary.alertChatError(
                player,
                messageHandler.get(player, "commons-friend-not-yourself")
        );
        return true;
    }

}

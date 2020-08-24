package net.astrocube.commons.bukkit.utils;

import me.yushust.message.core.MessageProvider;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class UserUtils {

    public static boolean checkSamePlayer(Player player, OfflinePlayer target, MessageProvider<Player> messageProvider) {

        if (!player.getUniqueId().equals(target.getUniqueId())) {
            return false;
        }

        ChatAlertLibrary.alertChatError(
                player,
                messageProvider.getMessage(player, "commons-friend-not-yourself")
        );
        return true;
    }

}

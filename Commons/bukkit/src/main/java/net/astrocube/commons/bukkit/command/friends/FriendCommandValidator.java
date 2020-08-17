package net.astrocube.commons.bukkit.command.friends;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.core.MessageProvider;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.bukkit.utils.ChatAlertLibrary;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import redis.clients.jedis.Jedis;

@Singleton
public class FriendCommandValidator {

    private @Inject MessageProvider<Player> messageProvider;
    private @Inject Redis redis;

    /**
     * Checks if the parameter "player" is equals to parameter "target"
     * and alerts the player.
     * @param player The player
     * @param target The offline player
     * @return True if the players are the same
     */
    public boolean checkSamePlayer(Player player, OfflinePlayer target) {

        if (!player.getUniqueId().equals(target.getUniqueId())) {
            return false;
        }

        ChatAlertLibrary.alertChatError(
                player,
                messageProvider.getMessage(player, "commons-friend-not-yourself")
        );
        return true;
    }

    /**
     * Checks if a friend request of player and target exists,
     * in that case, an alert is sent to the player
     * @param player The issuer
     * @param issuer The issuer user data
     * @param receiver The receiver
     * @return True if a friend request exists
     */
    public boolean checkAlreadySent(Player player, User issuer, User receiver) {

        try (Jedis jedis = redis.getRawConnection().getResource()) {
            if (jedis.hexists("friendship", issuer.getId() + ":" + receiver.getId())) {
                return false;
            }
        }

        ChatAlertLibrary.alertChatError(
                player,
                messageProvider.getMessage(player, "commons-friend-already-sent")
        );
        return true;
    }

}

package net.astrocube.api.bukkit.friend;

import net.astrocube.api.core.virtual.user.User;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public interface FriendHelper {

    /**
     * Checks if a friend request of player and target exists,
     * in that case, an alert is sent to the player
     * @param player The issuer
     * @param issuer The issuer user data
     * @param receiver The receiver
     * @return True if a friend request exists
     */
    boolean checkAlreadySent(Player player, User issuer, User receiver);

    /**
     * Checks if a friendship of a player and target does not exists,
     * in that case, an alert is sent to the player
     * @param player The issuer
     * @param issuer The issuer user data
     * @param receiver The receiver
     * @return True if a friendship exists
     */
    boolean checkNotFriends(Player player, User issuer, User receiver);

    /**
     * Checks if a friendship of a player and target exists,
     * in that case, an alert is sent to the player
     * @param player The issuer
     * @param issuer The issuer user data
     * @param receiver The receiver
     * @return True if a friendship exists
     */
    boolean checkAlreadyFriends(Player player, User issuer, User receiver);
}

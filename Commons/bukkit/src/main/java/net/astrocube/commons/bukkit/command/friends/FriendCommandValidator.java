package net.astrocube.commons.bukkit.command.friends;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.core.MessageProvider;
import net.astrocube.api.core.friend.FriendshipHandler;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.friend.Friendship;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.bukkit.utils.ChatAlertLibrary;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.logging.Level;

@Singleton
public class FriendCommandValidator {

    private @Inject MessageProvider<Player> messageProvider;
    private @Inject QueryService<Friendship> friendshipQueryService;
    private @Inject ObjectMapper objectMapper;
    private @Inject FriendshipHandler friendshipHandler;

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

        if (!friendshipHandler.existsFriendRequest(issuer.getId(), receiver.getId())) {
            return false;
        }

        ChatAlertLibrary.alertChatError(
                player,
                messageProvider.getMessage(player, "commons-friend-already-sent")
        );
        return true;
    }

    public boolean checkNotFriends(Player player, User issuer, User receiver) {

        Collection<Friendship> friendships = getFriendships(player, issuer, receiver);

        // why a collection can be null?
        // in this case, a empty collection
        // indicates a good case, but,
        // is possible a a bad case
        if (friendships == null) {
            return true;
        }

        if (friendships.isEmpty()) {
            ChatAlertLibrary.alertChatError(
                    player,
                    messageProvider.getMessage(player, "commons-friend-not-friends")
            );
        }

        return friendships.isEmpty();

    }

    /**
     * Checks if a friendship of a player and target exists,
     * in that case, an alert is sent to the player
     * @param player The issuer
     * @param issuer The issuer user data
     * @param receiver The receiver
     * @return True if a friendship exists
     */
    public boolean checkAlreadyFriends(Player player, User issuer, User receiver) {

        Collection<Friendship> friendships = getFriendships(player, issuer, receiver);

        // why a collection can be null?
        // in this case, a empty collection
        // indicates a good case, but,
        // is possible a a bad case
        if (friendships == null) {
            return true;
        }

        if (!friendships.isEmpty()) {
            ChatAlertLibrary.alertChatError(
                    player,
                    messageProvider.getMessage(player, "commons-friend-already-friends")
            );
        }

        return !friendships.isEmpty();

    }

    private Collection<Friendship> getFriendships(Player player, User issuer, User receiver) {

        ObjectNode filter = objectMapper.createObjectNode();
        filter.putArray("$or")
                .add(
                        objectMapper.createObjectNode()
                                .put("issuer", issuer.getId())
                                .put("receiver", receiver.getId())
                )
                .add(
                        objectMapper.createObjectNode()
                                .put("issuer", receiver.getId())
                                .put("receiver", issuer.getId())
                );

        Collection<Friendship> friendships;

        try {
            friendships = friendshipQueryService.querySync(filter).getFoundModels();
        } catch (Exception exception) {
            ChatAlertLibrary.alertChatError(player, exception.getMessage());
            Bukkit.getLogger().log(Level.SEVERE, "An error has appeared while querying friendships", exception);
            return null;
        }

        return friendships;

    }

}

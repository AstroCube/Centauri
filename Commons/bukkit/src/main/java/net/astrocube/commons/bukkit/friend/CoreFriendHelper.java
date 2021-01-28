package net.astrocube.commons.bukkit.friend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.friend.FriendHelper;
import net.astrocube.api.core.friend.FriendshipHandler;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.friend.Friendship;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.bukkit.utils.ChatAlertLibrary;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.logging.Level;

@Singleton
public class CoreFriendHelper implements FriendHelper {

    private @Inject MessageHandler messageHandler;
    private @Inject QueryService<Friendship> friendshipQueryService;
    private @Inject ObjectMapper objectMapper;
    private @Inject FriendshipHandler friendshipHandler;

    @Override
    public boolean checkAlreadySent(Player player, User issuer, User receiver) {

        if (!friendshipHandler.existsFriendRequest(issuer.getId(), receiver.getId())) {
            return false;
        }

        ChatAlertLibrary.alertChatError(
                player,
                messageHandler.get(player, "commons-friend-already-sent")
        );
        return true;
    }

    @Override
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
                    messageHandler.get(player, "commons-friend-not-friends")
            );
        }

        return friendships.isEmpty();

    }

    @Override
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
                    messageHandler.get(player, "commons-friend-already-friends")
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

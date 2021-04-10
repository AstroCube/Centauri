package net.astrocube.commons.bukkit.friend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.friend.FriendshipAction;
import net.astrocube.api.bukkit.friend.FriendshipActionEvent;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.core.concurrent.AsyncResponse;
import net.astrocube.api.core.concurrent.Callback;
import net.astrocube.api.core.concurrent.Response;
import net.astrocube.api.core.friend.FriendshipHandler;
import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.message.Messenger;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.service.create.CreateService;
import net.astrocube.api.core.service.paginate.PaginateResult;
import net.astrocube.api.core.service.paginate.PaginateService;
import net.astrocube.api.core.virtual.friend.Friendship;
import net.astrocube.api.core.virtual.friend.FriendshipDoc;
import net.astrocube.commons.core.utils.Callbacks;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import redis.clients.jedis.Jedis;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.logging.Level;

public class CoreFriendshipHandler implements FriendshipHandler {

    private static final int FRIEND_REQUEST_EXPIRY = 60; // 10 mintues

    private @Inject CreateService<Friendship, FriendshipDoc.Partial> createService;
    private @Inject PaginateService<Friendship> paginateService;
    private @Inject ObjectMapper objectMapper;
    private @Inject Plugin plugin;
    private @Inject Redis redis;
    private @Inject MessageHandler messageHandler;
    private final Channel<FriendshipAction> channel;

    @Inject
    public CoreFriendshipHandler(Messenger messenger) {
        this.channel = messenger.getChannel(FriendshipAction.class);
    }

    @Override
    public AsyncResponse<PaginateResult<Friendship>> paginate(String userId, int page, int perPage) {

        ObjectNode filter = objectMapper.createObjectNode();
        filter.putArray("$or")
                .add(
                        objectMapper.createObjectNode()
                            .put("sender", userId)
                )
                .add(
                        objectMapper.createObjectNode()
                            .put("receiver", userId)
                );

        return paginateService.paginate("?page=" + page + "&perPage=" + perPage, filter);
    }

    @Override
    public boolean existsFriendRequest(String from, String to) {
        try (Jedis jedis = redis.getRawConnection().getResource()) {
            return jedis.exists("friendship:" + from + ":" + to) ||
                    jedis.exists("friendship:" + to + ":" + from);
        }
    }

    @Override
    public void createFriendRequest(String from, String to) throws JsonProcessingException {

        if (existsFriendRequest(from, to)) {
            return;
        }

        try (Jedis jedis = redis.getRawConnection().getResource()) {

            jedis.set("friendship:" + from + ":" + to, ""); // dummy values
            jedis.expire("friendship:" + from + ":" + to, FRIEND_REQUEST_EXPIRY + 2);

            FriendshipAction createAction = createAction(from, to, FriendshipAction.Action.ADD);

            channel.sendMessage(
                    createAction,
                    new HashMap<>()
            );

            Bukkit.getScheduler().runTaskLaterAsynchronously(
                    plugin,
                    () -> {

                        try {
                            if (
                                    existsFriendRequest(from, to)
                            ) {

                                FriendshipAction action = createAction(from, to, FriendshipAction.Action.EXPIRE);

                                channel.sendMessage(
                                        action,
                                        new HashMap<>()
                                );

                                Bukkit.getPluginManager().callEvent(
                                        new FriendshipActionEvent(
                                                action
                                        )
                                );

                            }

                        } catch (JsonProcessingException e) {
                            plugin.getLogger().log(
                                    Level.SEVERE,
                                    "There was error announcing expiring friend request", e);
                        }

                    },
                    FRIEND_REQUEST_EXPIRY * 20L
            );

            Bukkit.getPluginManager().callEvent(new FriendshipActionEvent(createAction));

        }

    }

    @Override
    public void removeFriendRequest(String from, String to) {

        if (!existsFriendRequest(from, to)) {
            return;
        }

        try (Jedis jedis = redis.getRawConnection().getResource()) {
            jedis.del("friendship:" + from + ":" + to);
            jedis.del("friendship:" + to + ":" + from);
        }
    }

    @Override
    public void createFriendship(String sender, String receiver) {

        FriendshipDoc.Creation friendshipCreation = new FriendshipDoc.Creation() {

            @Nullable
            @Override
            public String getIssuer() {
                return sender;
            }

            @Override
            public boolean isAlerted() {
                return false;
            }

            @Override
            public String getSender() {
                return receiver;
            }

            @Override
            public String getReceiver() {
                return receiver;
            }

        };

        createService.create(friendshipCreation).callback(friendshipResponse -> {

            if (!friendshipResponse.isSuccessful()) {

                Player player = Bukkit.getPlayerByIdentifier(sender);

                if (player != null) {
                    messageHandler.sendIn(player, AlertModes.ERROR, "friend.error.internal");
                }

            }

            friendshipResponse.ifSuccessful(friendship -> {

                FriendshipAction action = createAction(sender, receiver, FriendshipAction.Action.ACCEPT);
                try {
                    channel.sendMessage(
                            action,
                            new HashMap<>()
                    );
                    Bukkit.getPluginManager().callEvent(new FriendshipActionEvent(action));
                } catch (JsonProcessingException e) {
                    plugin.getLogger().log(Level.SEVERE, "There was an error alerting friend accept", e);
                }

            });

        });

    }

    private FriendshipAction createAction(String sender, String receiver, FriendshipAction.Action action) {
        return new FriendshipAction() {
            @Override
            public FriendshipDoc.Relation getFriendship() {
                return new FriendshipDoc.Relation() {
                    @Override
                    public String getSender() {
                        return sender;
                    }

                    @Override
                    public String getReceiver() {
                        return receiver;
                    }
                };
            }

            @Override
            public Action getActionType() {
                return action;
            }
        };
    }

}

package net.astrocube.commons.bukkit.friend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import net.astrocube.api.core.concurrent.AsyncResponse;
import net.astrocube.api.core.concurrent.Callback;
import net.astrocube.api.core.friend.FriendshipHandler;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.service.create.CreateService;
import net.astrocube.api.core.service.paginate.PaginateResult;
import net.astrocube.api.core.service.paginate.PaginateService;
import net.astrocube.api.core.virtual.friend.Friendship;
import net.astrocube.api.core.virtual.friend.FriendshipDoc;
import net.astrocube.commons.core.utils.Callbacks;
import redis.clients.jedis.Jedis;

import javax.annotation.Nullable;

public class CoreFriendshipHandler implements FriendshipHandler {

    // expiry in seconds
    private static final int FRIEND_REQUEST_EXPIRY = 60; // 60 seconds = 1 minute

    private @Inject CreateService<Friendship, FriendshipDoc.Partial> createService;
    private @Inject PaginateService<Friendship> paginateService;
    private @Inject ObjectMapper objectMapper;
    private @Inject Redis redis;

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
            return jedis.exists("friendship:" + from + ":" + to);
        }
    }

    @Override
    public void createFriendRequest(String from, String to) {

        if (existsFriendRequest(from, to)) {
            return;
        }

        try (Jedis jedis = redis.getRawConnection().getResource()) {
            jedis.set("friendship:" + from + ":" + to, ""); // dummy values
            jedis.expire("friendship:" + from + ":" + to, FRIEND_REQUEST_EXPIRY);
        }


    }

    @Override
    public void removeFriendRequest(String from, String to) {
        if (!existsFriendRequest(from, to)) {
            return;
        }
        try (Jedis jedis = redis.getRawConnection().getResource()) {
            String key = "friendship:" + from + ":" + to;
            if (jedis.exists(key)) {
                jedis.del(key);
            }
        }
    }

    @Override
    public void createFriendship(String userOne, String userTwo, Callback<Friendship> callback) {

        FriendshipDoc.Creation friendshipCreation = new FriendshipDoc.Creation() {

            @Nullable
            @Override
            public String getIssuer() {
                return userOne;
            }

            @Override
            public boolean isAlerted() {
                return false;
            }

            @Override
            public String getSender() {
                return userTwo;
            }

            @Override
            public String getReceiver() {
                return userTwo;
            }

        };

        createService.create(friendshipCreation).callback(
                Callbacks.applyCommonErrorHandler("Friendship creation", callback)
        );

    }

}

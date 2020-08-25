package net.astrocube.commons.core.friend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import net.astrocube.api.core.concurrent.AsyncResponse;
import net.astrocube.api.core.friend.FriendshipHandler;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.service.paginate.PaginateResult;
import net.astrocube.api.core.service.paginate.PaginateService;
import net.astrocube.api.core.virtual.friend.Friendship;
import redis.clients.jedis.Jedis;

public class CoreFriendshipHandler implements FriendshipHandler {

    // expiry in seconds
    private static final int FRIEND_REQUEST_EXPIRY = 60; // 60 seconds = 1 minute

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
    public boolean existsFriendRequest(String issuer, String receiver) {
        try (Jedis jedis = redis.getRawConnection().getResource()) {
            return jedis.exists("friendship:" + issuer + ":" + receiver)
                    || jedis.exists("friendship:" + receiver + ":" + issuer);
        }
    }

    @Override
    public void createFriendRequest(String issuer, String receiver) {
        if (existsFriendRequest(issuer, receiver)) {
            return;
        }
        try (Jedis jedis = redis.getRawConnection().getResource()) {
            jedis.set("friendship:" + issuer + ":" + receiver, ""); // dummy values
            jedis.expire("friendship:" + issuer + ":" + receiver, FRIEND_REQUEST_EXPIRY);
        }
    }

}

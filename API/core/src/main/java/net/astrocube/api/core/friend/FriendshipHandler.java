package net.astrocube.api.core.friend;

import net.astrocube.api.core.concurrent.AsyncResponse;
import net.astrocube.api.core.concurrent.Callback;
import net.astrocube.api.core.service.paginate.PaginateResult;
import net.astrocube.api.core.virtual.friend.Friendship;

public interface FriendshipHandler {

    AsyncResponse<PaginateResult<Friendship>> paginate(String userId, int page, int perPage);

    boolean existsFriendRequest(String issuer, String receiver);

    void createFriendRequest(String issuer, String receiver);

    void removeFriendRequest(String issuer, String receiver);

    void createFriendship(String userOne, String userTwo, Callback<Friendship> callback);

}

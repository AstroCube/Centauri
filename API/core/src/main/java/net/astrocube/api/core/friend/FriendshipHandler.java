package net.astrocube.api.core.friend;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.astrocube.api.core.concurrent.AsyncResponse;
import net.astrocube.api.core.concurrent.Callback;
import net.astrocube.api.core.concurrent.Response;
import net.astrocube.api.core.service.paginate.PaginateResult;
import net.astrocube.api.core.virtual.friend.Friendship;

public interface FriendshipHandler {

    AsyncResponse<PaginateResult<Friendship>> paginate(String userId, int page, int perPage);

    boolean existsFriendRequest(String issuer, String receiver);

    void createFriendRequest(String issuer, String receiver) throws JsonProcessingException;

    void forceFriendship(String issuer, String first, String second, boolean alerted);

    void removeFriendRequest(String issuer, String receiver);

    void createFriendship(String issuer, String receiver);

}

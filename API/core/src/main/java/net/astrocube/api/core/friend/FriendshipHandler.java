package net.astrocube.api.core.friend;

import net.astrocube.api.core.concurrent.AsyncResponse;
import net.astrocube.api.core.service.paginate.PaginateResult;
import net.astrocube.api.core.virtual.friend.Friendship;

public interface FriendshipHandler {

    AsyncResponse<PaginateResult<Friendship>> paginate(String userId, int page, int perPage);

}

package net.astrocube.commons.core.friend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import net.astrocube.api.core.concurrent.AsyncResponse;
import net.astrocube.api.core.friend.FriendshipHandler;
import net.astrocube.api.core.service.paginate.PaginateResult;
import net.astrocube.api.core.service.paginate.PaginateService;
import net.astrocube.api.core.virtual.friend.Friendship;

public class CoreFriendshipHandler implements FriendshipHandler {

    private @Inject PaginateService<Friendship> paginateService;
    private @Inject ObjectMapper objectMapper;

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

}

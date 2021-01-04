package net.astrocube.commons.bungee.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.user.User;

import javax.inject.Inject;
import java.util.Optional;

public class UserProvideHelper {

    private @Inject QueryService<User> queryService;
    private @Inject ObjectMapper mapper;

    public Optional<User> getUserByName(String name) throws Exception {
        ObjectNode query = this.mapper.createObjectNode();
        query.put("username", name);
        return queryService.querySync(query).getFoundModels().stream().findFirst();
    }

}
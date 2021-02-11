package net.astrocube.commons.bukkit.user.staff;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gnu.trove.impl.hash.THash;
import net.astrocube.api.bukkit.user.staff.OnlineStaffProvider;
import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.group.Group;
import net.astrocube.api.core.virtual.user.User;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CoreOnlineStaffProvider implements OnlineStaffProvider {

    private @Inject QueryService<Group> groupQueryService;
    private @Inject QueryService<User> userQueryService;

    private @Inject ObjectMapper mapper;

    @Override
    public Set<User> provide() throws Exception {
        return getFromGroup(getGroups().stream().map(Group::getId).collect(Collectors.toSet()));
    }

    @Override
    public Set<Group> getGroups() throws Exception {

        ObjectNode staffQuery = this.mapper.createObjectNode();
        staffQuery.put("staff", true);

        return this.groupQueryService.querySync(staffQuery).getFoundModels();
    }

    @Override
    public Set<User> getFromGroup(Set<String> group) throws Exception {

        ObjectNode userQuery = this.mapper.createObjectNode();
        userQuery.put("session.online", true);

        ObjectNode inFilter = this.mapper.createObjectNode();
        inFilter.putPOJO("$in", group);

        userQuery.putPOJO("groups.group", inFilter);

        return this.userQueryService.querySync(userQuery).getFoundModels();
    }

    @Override
    public Set<User> getFromGroup(String group) throws Exception {
        Set<String> groupSet = new HashSet<>();
        groupSet.add(group);
        return getFromGroup(groupSet);
    }
}
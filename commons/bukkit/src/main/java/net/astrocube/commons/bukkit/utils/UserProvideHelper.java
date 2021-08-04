package net.astrocube.commons.bukkit.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.user.User;

import javax.inject.Inject;
import java.util.Optional;
import java.util.Set;

public class UserProvideHelper {

	private @Inject QueryService<User> queryService;
	private @Inject ObjectMapper mapper;

	public Optional<User> getUserByName(String name) {
		ObjectNode query = this.mapper.createObjectNode();
		query.put("username", name);

		Set<User> q = null;
		try {
			q = queryService.querySync(query).getFoundModels();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return q.stream().findFirst();
	}

}

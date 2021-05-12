package net.astrocube.commons.bukkit.game.match;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.match.ActualMatchProvider;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.service.query.QueryService;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

@Singleton
public class CoreActualMatchProvider implements ActualMatchProvider {

	private @Inject QueryService<Match> findService;
	private @Inject ObjectMapper objectMapper;

	@Override
	public Optional<Match> provide(String id) throws Exception {
		return getRegisteredMatches(id).stream().max(Comparator.comparing(Model.Stamped::getCreatedAt));
	}

	@Override
	public Set<Match> getRegisteredMatches(String id) throws Exception {

		ObjectNode query = (ObjectNode) objectMapper.readTree("{\n" +
			"\"$or\": [" +
			"{\"spectators\": \"" + id + "\"}," +
			"{\"pending\":" +
			"{\"responsible\": \"" + id + "\"}" +
			"},\n" +
			"{\"pending\": {\"involved\": \"" + id + "\"}}," +
			"{\"teams.members.user\": \"" + id + "\", \"teams.members.active\": true}" +
			"],\n" +
			"\"status\": {\"$nin\": [\"Finished\", \"Invalidated\"]}" +
			"}");

		return findService.querySync(query)
			.getFoundModels();
	}

}

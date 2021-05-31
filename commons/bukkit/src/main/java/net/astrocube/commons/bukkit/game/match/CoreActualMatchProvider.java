package net.astrocube.commons.bukkit.game.match;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.match.ActualMatchProvider;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.service.query.QueryService;

import java.util.Set;

@Singleton
public class CoreActualMatchProvider implements ActualMatchProvider {

	private @Inject QueryService<Match> findService;
	private @Inject ObjectMapper objectMapper;

	@Override
	public Set<Match> getRegisteredMatches(String id) throws Exception {

		// { spectators: [id] }
		ObjectNode query = objectMapper.createObjectNode();
		query.putArray("spectators").add(id);

		Set<Match> result = findService.querySync(query).getFoundModels();

		// { pending: [{ responsible: id }] }
		query = objectMapper.createObjectNode();
		query.putArray("pending").add(objectMapper.createObjectNode().put("responsible", id));
		result.addAll(findService.querySync(query).getFoundModels());

		// { teams: [{ members: [{ user: id, active: true }] }] }
		query = objectMapper.createObjectNode();
		ObjectNode teamQuery = objectMapper.createObjectNode();
		teamQuery.putArray("members")
			.add(objectMapper.createObjectNode().put("user", id).put("active", true));
		query.putArray("teams").add(teamQuery);
		result.addAll(findService.querySync(query).getFoundModels());

		// { status: NOT finished NOR invalidated }
		result.removeIf(match -> match.getStatus() == MatchDoc.Status.FINISHED
			|| match.getStatus() == MatchDoc.Status.INVALIDATED);

		return result;
	}

}

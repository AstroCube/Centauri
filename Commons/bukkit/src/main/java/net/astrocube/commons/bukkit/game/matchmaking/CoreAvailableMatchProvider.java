package net.astrocube.commons.bukkit.game.matchmaking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.match.AvailableMatchServerProvider;
import net.astrocube.api.bukkit.game.matchmaking.AvailableMatchProvider;
import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRequest;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class CoreAvailableMatchProvider implements AvailableMatchProvider {

	private @Inject QueryService<Match> matchQueryService;
	private @Inject FindService<GameMode> findService;
	private @Inject AvailableMatchServerProvider availableMatchServerProvider;
	private @Inject ObjectMapper mapper;

	@Override
	public Set<Match> getCriteriaAvailableMatches(MatchmakingRequest request) throws Exception {

		//ArrayNode serverArray = availableMatchServerProvider.getPairableServers(request);
		ObjectNode criteria = request.getCriteria().isPresent() ? request.getCriteria().get() : mapper.createObjectNode();

		GameMode gameMode = findService.findSync(request.getGameMode());

		if (gameMode.getSubTypes() == null) {
			throw new GameControlException("There are not available sub modes for this GameMode");
		}

		Optional<SubGameMode> subGameModeOptional = gameMode.getSubTypes().stream()
			.filter(sub -> sub.getId().equalsIgnoreCase(request.getSubGameMode())).findFirst();

		if (!subGameModeOptional.isPresent()) {
			throw new GameControlException("There was no subMode matching this request at the database");
		}

		//criteria.put("server", serverArray);
		criteria.put("gamemode", gameMode.getId());
		criteria.put("subGamemode", subGameModeOptional.get().getId());
		criteria.put("private", false);
		criteria.putPOJO("status", MatchDoc.Status.LOBBY);

		System.out.println("MatchQueryService");

		return matchQueryService.querySync(criteria).getFoundModels()
			.stream()
			.filter(match ->
				(subGameModeOptional.get().getMaxPlayers() - CoreAvailableMatchProvider.getRemainingSpace(match)) >=
					(request.getRequesters().getInvolved().size() + 1))
			.collect(Collectors.toSet());
	}

	public static int getRemainingSpace(Match match) {
		int total = 0;
		for (MatchAssignable matchAssignable : match.getPending()) {
			total += matchAssignable.getInvolved().size() + 1;
		}
		return total;
	}

}

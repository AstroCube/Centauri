package net.astrocube.commons.bukkit.game.matchmaking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.match.AvailableMatchServerProvider;
import net.astrocube.api.bukkit.game.matchmaking.AvailableMatchProvider;
import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRequest;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.server.Server;

import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class CoreAvailableMatchProvider implements AvailableMatchProvider {

    private @Inject QueryService<Match> matchQueryService;
    private @Inject AvailableMatchServerProvider availableMatchServerProvider;
    private @Inject ObjectMapper mapper;

    @Override
    public Set<Match> getCriteriaAvailableMatches(MatchmakingRequest request) throws Exception {

        ArrayNode serverArray = availableMatchServerProvider.getPairableServers(request);
        ObjectNode criteria = request.getCriteria().isPresent() ? request.getCriteria().get() : mapper.createObjectNode();

        criteria.put("server", serverArray);
        criteria.putPOJO("status", MatchDoc.Status.LOBBY);

        return matchQueryService.querySync(criteria).getFoundModels()
                .stream()
                .filter(match ->
                        match.getStatus() == MatchDoc.Status.LOBBY &&
                        CoreAvailableMatchProvider.getRemainingSpace(match) >= (request.getRequesters().getInvolved().size() + 1))
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

package net.astrocube.commons.bukkit.game.matchmaking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.matchmaking.AvailableMatchProvider;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRequest;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.server.Server;

import java.util.Set;

@Singleton
public class CoreAvailableMatchProvider implements AvailableMatchProvider {

    private @Inject QueryService<Server> serverQueryService;
    private @Inject QueryService<Match> matchQueryService;
    private @Inject ObjectMapper mapper;

    @Override
    public Set<Match> getCriteriaAvailableMatches(MatchmakingRequest request) throws Exception {

        ObjectNode node = mapper.createObjectNode();
        node.put("gamemode", request.getGameMode());
        node.put("subGamemode", request.getSubGameMode());

        ObjectNode criteria = request.getCriteria().isPresent() ? request.getCriteria().get() : mapper.createObjectNode();
        ObjectNode operator = mapper.createObjectNode();
        ArrayNode serverArray = mapper.createArrayNode();

        for (Server server : serverQueryService.querySync(node).getFoundModels()) {
            serverArray.add(server.getId());
        }

        MatchDoc.Status lobby = MatchDoc.Status.LOBBY;

        operator.put("$in", serverArray);
        criteria.put("server", operator);
        criteria.put("status", lobby.toString().substring(0, 1).toUpperCase() + lobby.toString().substring(1));

        //TODO: Check capacity of matchmaking criteria

        return matchQueryService.querySync(criteria).getFoundModels();
    }

}

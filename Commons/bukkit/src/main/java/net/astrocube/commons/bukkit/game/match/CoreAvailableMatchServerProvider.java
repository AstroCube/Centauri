package net.astrocube.commons.bukkit.game.match;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.match.AvailableMatchServerProvider;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRequest;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.server.Server;

@Singleton
public class CoreAvailableMatchServerProvider implements AvailableMatchServerProvider {

    private @Inject QueryService<Server> serverQueryService;
    private @Inject ObjectMapper mapper;

    @Override
    public ArrayNode getPairableServers(MatchmakingRequest request) throws Exception {

        ObjectNode node = mapper.createObjectNode();
        node.put("gameMode", request.getGameMode());
        node.put("subGameMode", request.getSubGameMode());
        request.getMap().ifPresent(map -> {
            if (!map.equalsIgnoreCase("")) {
                node.put("map", map);
            }
        });

        ArrayNode serverArray = mapper.createArrayNode();

        for (Server server: serverQueryService.querySync(node).getFoundModels()) {
            serverArray.add(server.getId());
        }

        return serverArray;
    }
}

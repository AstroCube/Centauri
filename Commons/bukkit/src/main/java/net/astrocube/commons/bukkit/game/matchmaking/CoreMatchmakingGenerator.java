package net.astrocube.commons.bukkit.game.matchmaking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingGenerator;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRegistryHandler;
import net.astrocube.api.core.server.ServerService;
import net.astrocube.api.core.virtual.server.Server;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

@Singleton
public class CoreMatchmakingGenerator implements MatchmakingGenerator {

    private @Inject MatchmakingRegistryHandler matchmakingRegistryHandler;
    private @Inject ServerService serverService;

    private @Inject ObjectMapper mapper;

    @Override
    public void pairMatch(Player player) throws Exception {

        Server server = serverService.getActual();

        MatchAssignable assignable = new MatchAssignable() {
            @Override
            public String getResponsible() {
                return player.getDatabaseIdentifier();
            }

            @Override
            public Set<String> getInvolved() {
                return new HashSet<>();  // TODO: Get party
            }
        };

        matchmakingRegistryHandler.generateRequest(
                assignable,
                server.getGameMode(),
                server.getSubGameMode(),
                "",
                mapper.createObjectNode()
        );

    }

}

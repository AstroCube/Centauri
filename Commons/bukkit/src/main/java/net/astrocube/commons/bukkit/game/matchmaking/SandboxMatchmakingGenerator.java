package net.astrocube.commons.bukkit.game.matchmaking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRegistryHandler;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingGenerator;
import net.astrocube.api.core.server.ServerService;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import net.astrocube.api.core.virtual.server.Server;
import net.astrocube.api.bukkit.translation.mode.AlertMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

@Singleton
public class SandboxMatchmakingGenerator implements MatchmakingGenerator {

    private @Inject MessageHandler<Player> messageHandler;
    private @Inject MatchmakingRegistryHandler matchmakingRegistryHandler;
    private @Inject ObjectMapper mapper;
    private @Inject ServerService serverService;
    private @Inject Plugin plugin;

    @Override
    public void pairMatch(Player player, GameMode gameMode, SubGameMode subMode) throws Exception {

        Server server = serverService.getActual();

        if (!plugin.getConfig().getBoolean("server.sandbox")) {
            messageHandler.send(player, AlertMode.ERROR,"game.admin.no-sandbox");
            return;
        }

        MatchAssignable assignable = new MatchAssignable() {
            @Override
            public String getResponsible() {
                return player.getDatabaseIdentifier();
            }

            @Override
            public Set<String> getInvolved() {
                return new HashSet<>();
            }
        };

        ObjectNode node = mapper.createObjectNode();
        node.put("server", server.getId());

        matchmakingRegistryHandler.generateRequest(
                assignable,
                gameMode.getId(),
                subMode.getId(),
                "",
                node
        );
    }
}
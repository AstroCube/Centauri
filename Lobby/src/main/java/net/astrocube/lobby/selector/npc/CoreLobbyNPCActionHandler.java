package net.astrocube.lobby.selector.npc;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingGenerator;
import net.astrocube.api.bukkit.lobby.selector.npc.LobbyNPCActionHandler;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.core.cloud.CloudTeleport;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.logging.Level;

@Singleton
public class CoreLobbyNPCActionHandler implements LobbyNPCActionHandler {

    private @Inject MatchmakingGenerator matchmakingGenerator;
    private @Inject Plugin plugin;
    private @Inject FindService<GameMode> findService;
    private @Inject MessageHandler messageHandler;
    private @Inject CloudTeleport cloudTeleport;

    @Override
    public void execute(Player player, String mode, String subMode) {

        System.out.println("Executed");

        findService.find(mode).callback(response -> {

            response.ifSuccessful(gameMode -> {

                System.out.println("Oraguey");

                if (subMode.isEmpty()) {
                    cloudTeleport.teleportToGroup(gameMode.getLobby(), player.getName());
                    return;
                }

                System.out.println("Not null");

                if (gameMode.getSubTypes() != null) {

                    Optional<SubGameMode> subGameMode = gameMode.getSubTypes()
                            .stream()
                            .filter(g -> g.getId().equalsIgnoreCase(subMode))
                            .findAny();

                    System.out.println("Asshole");

                    subGameMode.ifPresent(subModeRecord -> {

                        try {
                            System.out.println("Gone here");
                            matchmakingGenerator.pairMatch(player, gameMode, subModeRecord);
                        } catch (Exception e) {
                            messageHandler.sendIn(player, AlertModes.ERROR, "selectors.error");
                            plugin.getLogger().log(Level.SEVERE, "There was an error pairing to match", e);
                        }

                    });

                }

            });

        });

    }

}

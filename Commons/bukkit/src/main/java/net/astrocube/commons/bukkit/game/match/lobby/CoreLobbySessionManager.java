package net.astrocube.commons.bukkit.game.match.lobby;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.countdown.CountdownScheduler;
import net.astrocube.api.bukkit.game.lobby.LobbySessionManager;
import net.astrocube.api.bukkit.game.match.ActualMatchProvider;
import net.astrocube.api.bukkit.game.match.MatchService;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.concurrent.Response;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import net.astrocube.commons.bukkit.game.match.control.CoreMatchParticipantsProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.Set;

@Singleton
public class CoreLobbySessionManager implements LobbySessionManager {

    private @Inject FindService<GameMode> findService;
    private @Inject MessageHandler<Player> messageHandler;
    private @Inject ActualMatchProvider actualMatchProvider;
    private @Inject MatchService matchService;
    private @Inject CountdownScheduler countdownScheduler;
    private @Inject Plugin plugin;

    @Override
    public void connectUser(Player player, Match match) {

        findService.find(match.getGameMode()).callback(gameModeResponse -> {

            if (ensureInvalidation(gameModeResponse, match)) {
                return;
            }

            SubGameMode subGameMode = getAssignedMode(gameModeResponse.getResponse().get(), match).get();

            Bukkit.getScheduler().runTask(plugin, () -> {
                player.teleport(LobbyLocationParser.getLobby());
                player.setGameMode(org.bukkit.GameMode.ADVENTURE);

                Set<String> waitingIds = CoreMatchParticipantsProvider.getPendingIds(match);

                Bukkit.getOnlinePlayers().forEach(online -> {
                    if (waitingIds.contains(online.getDatabaseIdentifier())) {

                        player.sendMessage(
                                messageHandler.getMessage("game.lobby-join")
                                        .replace("%%player%%", online.getDisplayName())
                                        .replace("%%actual%%", waitingIds.size() + "")
                                        .replace("%%max%%", subGameMode.getMaxPlayers() + "")
                        );

                    } else {
                        online.hidePlayer(player);
                        player.hidePlayer(online);
                    }
                });

                if (waitingIds.size() >= subGameMode.getMinPlayers()) {
                    countdownScheduler.scheduleMatchCountdown(match);
                }

            });



        });

    }

    @Override
    public void disconnectUser(Player player) throws Exception {

        Optional<Match> matchOptional = actualMatchProvider.provide(player.getDatabaseIdentifier());

        if (matchOptional.isPresent()) {

            Match match = matchOptional.get();

            if (match.getSpectators().contains(player.getDatabaseIdentifier())) {
                matchService.assignSpectator(player.getDatabaseIdentifier(), match.getId(), false);
            } else if (match.getPending().stream().anyMatch(pending ->
                    pending.getResponsible().equalsIgnoreCase(player.getDatabaseIdentifier()) ||
                    pending.getInvolved().contains(player.getDatabaseIdentifier()))
            ) {

                //TODO: Perform disconnect in Database



                findService.find(match.getGameMode()).callback(gameModeResponse -> {

                    if (ensureInvalidation(gameModeResponse, match)) {
                        return;
                    }

                    SubGameMode subGameMode = getAssignedMode(gameModeResponse.getResponse().get(), match).get();

                    Set<String> waitingIds = CoreMatchParticipantsProvider.getPendingIds(match);

                    Bukkit.getOnlinePlayers().forEach(online -> {
                        if (waitingIds.contains(online.getDatabaseIdentifier())) {

                            player.sendMessage(
                                    messageHandler.getMessage("game.lobby-leave")
                                            .replace("%%player%%", online.getDisplayName())
                                            .replace("%%actual%%", (waitingIds.size() + 1) + "")
                                            .replace("%%max%%", subGameMode.getMaxPlayers() + "")
                            );

                        } else {
                            online.hidePlayer(player);
                            player.hidePlayer(online);
                        }
                    });

                });

            } else if (match.getTeams().stream().anyMatch(m -> m.getMembers().contains(player.getDatabaseIdentifier()))) {

            }

        }

    }

    public boolean ensureInvalidation(Response<GameMode> gameMode, Match match) {

        if (!gameMode.isSuccessful() || !gameMode.getResponse().isPresent()) {
            Bukkit.getLogger().warning("There was an error while updating the match assignation.");
            //TODO: Expulse user
            return true;
        }

        Optional<SubGameMode> subGameMode = getAssignedMode(gameMode.getResponse().get(), match);

        if (!subGameMode.isPresent()) {
            Bukkit.getLogger().warning("There was an error while updating the match assignation.");
            //TODO: Expulse user
            return true;
        }

        return false;
    }

    public Optional<SubGameMode> getAssignedMode(GameMode mode, Match match) {
        return mode
                .getSubTypes()
                .stream()
                .filter(sub -> sub.getId().equalsIgnoreCase(match.getSubMode()))
                .findFirst();
    }


}

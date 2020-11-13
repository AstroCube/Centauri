package net.astrocube.commons.bukkit.game.match.lobby;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.lobby.LobbySessionManager;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.server.ServerService;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class CoreLobbySessionManager implements LobbySessionManager {

    private @Inject FindService<GameMode> findService;
    private @Inject MessageHandler<Player> messageHandler;
    private @Inject DisplayMatcher displayMatcher;

    @Override
    public void connectUser(Player player, Match match) {

        findService.find(match.getGameMode()).callback(gameModeResponse -> {


            if (!gameModeResponse.isSuccessful() || !gameModeResponse.getResponse().isPresent()) {
                Bukkit.getLogger().warning("There was an error while updating the match assignation.");
                //TODO: Expulse user
            }

            Optional<SubGameMode> subGameMode = gameModeResponse.getResponse().get()
                    .getSubTypes()
                    .stream()
                    .filter(sub -> sub.getId().equalsIgnoreCase(match.getSubMode()))
                    .findFirst();

            if (!subGameMode.isPresent()) {
                Bukkit.getLogger().warning("There was an error while updating the match assignation.");
                //TODO: Expulse user
            }

            player.teleport(LobbyLocationParser.getLobby());
            player.setGameMode(org.bukkit.GameMode.ADVENTURE);

            Set<String> waitingIds = match.getPending().stream()
                    .map(assignable -> {
                        assignable.getInvolved().add(assignable.getResponsible());
                        return assignable.getInvolved();
                    })
                    .flatMap(Collection::stream).collect(Collectors.toSet());

            Bukkit.getOnlinePlayers().forEach(online -> {
                if (waitingIds.contains(online.getDatabaseIdentifier())) {

                    player.sendMessage(
                            messageHandler.getMessage("game.lobby-join")
                            .replace("%%player%%", online.getDisplayName())
                            .replace("%%%actual%%", waitingIds.size() + "")
                            .replace("%%max%%", subGameMode.get().getMaxPlayers() + "")
                    );

                } else {
                    online.hidePlayer(player);
                    player.hidePlayer(online);
                }
            });
        });

    }

    @Override
    public void disconnectUser(Player player) {

    }


}

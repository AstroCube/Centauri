package net.astrocube.commons.bukkit.listener.game;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.event.GameUserJoinEvent;
import net.astrocube.api.bukkit.game.lobby.LobbySessionManager;
import net.astrocube.api.bukkit.game.match.UserMatchJoiner;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.find.FindService;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LobbyUserJoinListener implements Listener {

    private @Inject FindService<Match> findService;
    private @Inject LobbySessionManager lobbySessionManager;

    @EventHandler
    public void onGameUserJoin(GameUserJoinEvent event) {

        if (event.getStatus().equals(UserMatchJoiner.Status.WAITING)) {
            findService.find(event.getMatch()).callback(matchResponse -> {

                if (!matchResponse.isSuccessful() || !matchResponse.getResponse().isPresent()) {
                    Bukkit.getLogger().warning("There was an error while updating the match assignation.");
                    //TODO: Expulse user
                }

                lobbySessionManager.connectUser(event.getPlayer(), matchResponse.getResponse().get());

            });
        }

    }

}

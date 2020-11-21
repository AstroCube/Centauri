package net.astrocube.lobby.selector.lobby;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.lobby.selector.lobby.LobbySelectorWrapper;
import net.astrocube.api.bukkit.lobby.selector.lobby.LobbyServerRedirect;
import net.astrocube.api.bukkit.lobby.selector.lobby.LobbySwitchStatus;
import org.bukkit.entity.Player;

@Singleton
public class CoreLobbyServerRedirect implements LobbyServerRedirect {

    private @Inject MessageHandler<Player> messageHandler;

    @Override
    public void redirectPlayer(Player player, LobbySelectorWrapper wrapper, LobbySwitchStatus status) {

        switch (status) {
            case FULL: {
                messageHandler.send(player, "lobby.lobby-selector.error.full");
                break;
            }
            case CYCLIC: {
                messageHandler.send(player, "lobby.lobby-selector.error.cyclic");
                break;
            }
            default: {
                //TODO: Redirect player
                break;
            }
        }

    }

}

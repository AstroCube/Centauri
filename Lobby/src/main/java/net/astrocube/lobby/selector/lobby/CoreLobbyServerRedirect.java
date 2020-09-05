package net.astrocube.lobby.selector.lobby;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.core.MessageProvider;
import net.astrocube.api.bukkit.lobby.selector.lobby.LobbySelectorWrapper;
import net.astrocube.api.bukkit.lobby.selector.lobby.LobbyServerRedirect;
import net.astrocube.api.bukkit.lobby.selector.lobby.LobbySwitchStatus;
import org.bukkit.entity.Player;

@Singleton
public class CoreLobbyServerRedirect implements LobbyServerRedirect {

    private @Inject MessageProvider<Player> messageProvider;

    @Override
    public void redirectPlayer(Player player, LobbySelectorWrapper wrapper, LobbySwitchStatus status) {

        switch (status) {
            case FULL: {
                messageProvider.sendMessage(player, "lobby.lobby-selector.error.full");
                break;
            }
            case CYCLIC: {
                messageProvider.sendMessage(player, "lobby.lobby-selector.error.cyclic");
                break;
            }
            default: {
                //TODO: Redirect player
                break;
            }
        }

    }

}

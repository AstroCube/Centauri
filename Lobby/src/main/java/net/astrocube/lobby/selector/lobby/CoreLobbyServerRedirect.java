package net.astrocube.lobby.selector.lobby;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.lobby.selector.lobby.LobbyServerRedirect;
import net.astrocube.api.bukkit.lobby.selector.lobby.LobbySwitchStatus;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.core.cloud.CloudInstanceProvider;
import net.astrocube.api.core.cloud.CloudTeleport;
import org.bukkit.entity.Player;

@Singleton
public class CoreLobbyServerRedirect implements LobbyServerRedirect {

    private @Inject MessageHandler messageHandler;
    private @Inject CloudTeleport cloudTeleport;

    @Override
    public void redirectPlayer(Player player, CloudInstanceProvider.Instance wrapper, LobbySwitchStatus status) {

        switch (status) {
            case FULL: {
                messageHandler.send(player, AlertModes.ERROR, "lobby.lobby-selector.error.full");
                break;
            }
            case CYCLIC: {
                messageHandler.send(player, AlertModes.ERROR, "lobby.lobby-selector.error.cyclic");
                break;
            }
            default: {
                cloudTeleport.teleportToServer(wrapper.getName(), player.getName());
                break;
            }
        }

    }

}

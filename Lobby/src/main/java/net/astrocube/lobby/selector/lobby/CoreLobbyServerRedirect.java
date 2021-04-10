package net.astrocube.lobby.selector.lobby;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.lobby.selector.lobby.LobbyServerRedirect;
import net.astrocube.api.bukkit.lobby.selector.lobby.LobbySwitchStatus;
import net.astrocube.api.bukkit.teleport.ServerTeleportRetry;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.core.cloud.CloudInstanceProvider;
import org.bukkit.entity.Player;

@Singleton
public class CoreLobbyServerRedirect implements LobbyServerRedirect {

    private @Inject MessageHandler messageHandler;
    private @Inject ServerTeleportRetry serverTeleportRetry;

    @Override
    public void redirectPlayer(Player player, CloudInstanceProvider.Instance wrapper, LobbySwitchStatus status) {

        switch (status) {
            case FULL: {
                messageHandler.sendIn(player, AlertModes.ERROR, "lobby.lobby-selector.error.full");
                break;
            }
            case CYCLIC: {
                messageHandler.sendIn(player, AlertModes.ERROR, "lobby.lobby-selector.error.cyclic");
                break;
            }
            default: {
                serverTeleportRetry.attemptTeleport(player.getName(), wrapper.getName(), 1,3);
                break;
            }
        }

    }

}

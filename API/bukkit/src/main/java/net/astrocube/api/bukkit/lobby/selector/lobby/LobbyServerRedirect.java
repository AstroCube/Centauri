package net.astrocube.api.bukkit.lobby.selector.lobby;

import net.astrocube.api.core.cloud.CloudInstanceProvider;
import org.bukkit.entity.Player;

public interface LobbyServerRedirect {

    /**
     * Redirect player to specified lobby selector
     * @param player to be redirected
     * @param wrapper where validations will be done
     * @param status to decide user switching
     */
    void redirectPlayer(Player player, CloudInstanceProvider.Instance wrapper, LobbySwitchStatus status);

}

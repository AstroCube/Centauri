package net.astrocube.api.bukkit.lobby.selector.lobby;

import org.bukkit.entity.Player;

public interface LobbyServerRedirect {

    /**
     * Redirect player to specified lobby selector
     * @param player to be redirected
     * @param wrapper where validations will be done
     * @param status to decide user switching
     */
    void redirectPlayer(Player player, LobbySelectorWrapper wrapper, LobbySwitchStatus status);

}

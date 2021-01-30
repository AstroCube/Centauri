package net.astrocube.api.bukkit.game.spectator;

import org.bukkit.entity.Player;

public interface LobbyItemProvider {

    /**
     * Provide map item for a player
     * @param player where item will be place
     * @param slot to be held
     */
    void provide(Player player, int slot);

}

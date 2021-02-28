package net.astrocube.api.bukkit.game.spectator;

import org.bukkit.entity.Player;

public interface LobbyItemProvider {

    /**
     * Provide map item for a player
     * @param player where item will be place
     * @param slot to be held
     */
    void provideBackButton(Player player, int slot);

    /**
     * Provide admin item for game control
     * @param player where item will be placed
     * @param slot to be held
     */
    void provideAdminButton(Player player, int slot);

}

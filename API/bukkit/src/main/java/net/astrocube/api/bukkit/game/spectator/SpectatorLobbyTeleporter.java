package net.astrocube.api.bukkit.game.spectator;

import org.bukkit.entity.Player;

public interface SpectatorLobbyTeleporter {

    /**
     * Schedule teleport back to lobby
     * @param player to teleport
     */
    void scheduleTeleport(Player player);

    /**
     * Cancel scheduled teleport to lobby
     * @param player to cancel
     */
    void cancelTeleport(Player player);

    /**
     * Check if player has scheduled teleport
     * @param player to check
     * @return if player has scheduled
     */
    boolean hasScheduledTeleport(Player player);

}

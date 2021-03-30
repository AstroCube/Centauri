package net.astrocube.api.bukkit.lobby.selector.npc;

import org.bukkit.entity.Player;

public interface SelectorRegistry {

    /**
     * Creates registry from configuration
     */
    void generateRegistry();

    /**
     * Spawns NPC selector for player
     * @param player to spawn
     */
    void spawnSelectors(Player player);

    /**
     * Despawns NPC selector for player
     * @param player to despawn
     */
    void despawnSelectors(Player player);

}

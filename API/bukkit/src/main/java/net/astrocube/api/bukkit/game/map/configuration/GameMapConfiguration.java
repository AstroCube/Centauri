package net.astrocube.api.bukkit.game.map.configuration;

import net.astrocube.api.core.virtual.gamemode.GameMode;

/**
 * Base interface in order to homogenize all the
 * {@link GameMode} configurations.
 */
public interface GameMapConfiguration {

    /**
     * @return spectator point where users will be spawned.
     */
    CoordinatePoint getSpectatorSpawn();

}

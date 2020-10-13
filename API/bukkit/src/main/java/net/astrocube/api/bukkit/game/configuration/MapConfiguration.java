package net.astrocube.api.bukkit.game.configuration;

import net.astrocube.api.bukkit.virtual.game.map.GameMap;

/**
 * Interface providing {@link GameMap} base configuration properties and
 * serves as placeholder for Game-Control based configuration.
 */
public interface MapConfiguration {

    /**
     * @return coordinates set where user is teleported after disqualification.
     */
    Coordinates getSpectator();

}

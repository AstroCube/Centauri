package net.astrocube.api.bukkit.game.configuration;

import org.bukkit.Location;

/**
 * Small coordinate set independent of Bukkit {@link Location}
 */
public interface Coordinates {

    /**
     * @return X coordinate at a certain point
     */
    float getX();

    /**
     * @return Y coordinate at a certain point
     */
    float getY();
    /**
     * @return Z coordinate at a certain point
     */
    float getZ();

}

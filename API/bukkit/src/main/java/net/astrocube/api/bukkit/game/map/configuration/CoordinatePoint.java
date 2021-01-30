package net.astrocube.api.bukkit.game.map.configuration;

import org.bukkit.Location;
import org.bukkit.World;

/**
 * World-less coordinates util for {@link GameMapConfiguration}s.
 */
public interface CoordinatePoint {

    float getX();

    float getY();

    float getZ();

    static Location coordinatePointToLocation(World world, CoordinatePoint coordinatePoint) {
        return new Location(world, coordinatePoint.getX(), coordinatePoint.getY(), coordinatePoint.getZ());
    }

}

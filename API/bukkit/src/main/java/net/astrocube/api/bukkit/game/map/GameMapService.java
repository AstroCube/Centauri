package net.astrocube.api.bukkit.game.map;

import net.astrocube.api.bukkit.virtual.game.map.GameMap;
import net.astrocube.api.core.service.find.FindService;

import java.io.IOException;
import java.net.MalformedURLException;

public interface GameMapService extends FindService<GameMap> {

    /**
     * @param id of the desired map.
     * @return byte array containing a slime format map.
     */
    byte[] getMapWorld(String id) throws IOException;

    /**
     * @param id of the desired configuration.
     * @return byte array containing map configuration.
     */
    byte[] getMapConfiguration(String id) throws IOException;

}

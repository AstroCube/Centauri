package net.astrocube.api.bukkit.game.map;

import net.astrocube.api.bukkit.virtual.game.map.GameMap;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.service.query.QueryService;

import java.io.IOException;
import java.util.Optional;

public interface GameMapService {

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

    /**
     * @param gameMode id to be retrieved
     * @param subGameMode id to be retrieved
     * @return a random map id
     */
    Optional<GameMap> getRandomMap(String gameMode, String subGameMode) throws Exception;

}

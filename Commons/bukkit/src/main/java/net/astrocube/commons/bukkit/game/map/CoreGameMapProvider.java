package net.astrocube.commons.bukkit.game.map;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.map.GameMapCache;
import net.astrocube.api.bukkit.game.map.GameMapProvider;
import net.astrocube.api.bukkit.game.map.GameMapService;
import net.astrocube.api.bukkit.virtual.game.map.GameMap;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class CoreGameMapProvider implements GameMapProvider {

    private @Inject GameMapService gameMapService;
    private @Inject GameMapCache gameMapCache;

    @Override
    public void loadGameMap(GameMap gameMap) throws IOException, GameControlException {

        byte[] serializedMap = gameMapCache.exists(gameMap.getId()) ?
                gameMapCache.getFile(gameMap.getId()) :
                gameMapService.getMapWorld(gameMap.getId());

        byte[] serializedConfig = gameMapCache.exists(gameMap.getId()) ?
                gameMapCache.getConfiguration(gameMap.getId()) :
                gameMapService.getMapConfiguration(gameMap.getId());

        if (!gameMapCache.exists(gameMap.getId())) {
            gameMapCache.registerFile(gameMap.getId(), serializedMap);
            gameMapCache.registerConfiguration(gameMap.getId(), serializedConfig);
        }

        generateEmptyFolder();
        File file = generateMapFolder(gameMap.getId());

        if (!file.exists()) {
            file.mkdir();
            FileUtils.writeByteArrayToFile(new File(file, "map.slime"), serializedMap);
            FileUtils.writeByteArrayToFile(new File(file, "config.json"), serializedConfig);
        }

    }

    @Override
    public Set<GameMap> getAvailableMaps() {
        return null;
    }

    private void generateEmptyFolder() {
        File file = new File("./maps");
        if (!file.exists()) {
            file.mkdir();
        }
    }

    private File generateMapFolder(String id) {
        File file = new File("./maps/" + id);
        return file;
    }

}

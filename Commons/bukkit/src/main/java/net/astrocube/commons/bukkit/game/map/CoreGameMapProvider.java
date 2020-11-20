package net.astrocube.commons.bukkit.game.map;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.map.GameMapCache;
import net.astrocube.api.bukkit.game.map.GameMapProvider;
import net.astrocube.api.bukkit.game.map.GameMapService;
import net.astrocube.api.bukkit.virtual.game.map.GameMap;
import net.astrocube.slime.api.world.SlimeWorld;
import net.astrocube.slime.api.world.properties.SlimePropertyMap;
import net.astrocube.slime.core.loaders.LoaderUtils;

import java.io.IOException;

public class CoreGameMapProvider implements GameMapProvider {

    private @Inject GameMapService gameMapService;
    private @Inject GameMapCache gameMapCache;

    @Override
    public SlimeWorld loadGameMap(GameMap gameMap) throws IOException, GameControlException {

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

        return LoaderUtils.deserializeWorld(loader, worldName, serializedWorld, new SlimePropertyMap(), readOnly);
    }

}

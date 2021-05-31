package net.astrocube.commons.bukkit.game.map;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.map.GameMapCache;
import net.astrocube.api.bukkit.game.map.GameMapProvider;
import net.astrocube.api.bukkit.game.map.GameMapService;
import net.astrocube.api.bukkit.virtual.game.map.GameMap;

import java.io.IOException;

@Singleton
public class CoreGameMapProvider implements GameMapProvider {

	private @Inject GameMapService gameMapService;
	private @Inject GameMapCache gameMapCache;

	@Override
	public MapFiles loadGameMap(GameMap gameMap) throws IOException, GameControlException {

		boolean cached = gameMapCache.exists(gameMap.getId());

		byte[] serializedMap = cached
			? gameMapCache.getFile(gameMap.getId())
			: gameMapService.getMapWorld(gameMap.getId());

		byte[] serializedConfig = cached
			? gameMapCache.getConfiguration(gameMap.getId())
			: gameMapService.getMapConfiguration(gameMap.getId());

		if (!cached) {
			gameMapCache.registerFile(gameMap.getId(), serializedMap);
			gameMapCache.registerConfiguration(gameMap.getId(), serializedConfig);
		}

		return new MapFiles(serializedMap, serializedConfig);
	}

}

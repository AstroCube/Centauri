package net.astrocube.api.bukkit.game.map;

import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.virtual.game.map.GameMap;
import net.astrocube.slime.api.world.SlimeWorld;

import java.io.IOException;

public interface GameMapProvider {

	/**
	 * Retrieve a map file pair from cache.
	 * @param gameMap to be retrieved.
	 * @return map pair of configuration/file.
	 * @throws IOException          when map was not obtained correctly
	 * @throws GameControlException when map is attempted to be obtained
	 *                              wrongly without being cached before. This should never happen.
	 */
	MapFiles loadGameMap(GameMap gameMap) throws IOException, GameControlException;

	interface MapFiles {

		byte[] getMapFile();

		byte[] getMapConfig();

	}

	interface EncodedFiles {

		SlimeWorld getWorld();

		String getConfiguration();

	}

}
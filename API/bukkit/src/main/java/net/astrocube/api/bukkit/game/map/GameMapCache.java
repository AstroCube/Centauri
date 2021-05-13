package net.astrocube.api.bukkit.game.map;

import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.virtual.game.map.GameMap;
import net.astrocube.api.core.redis.Redis;

public interface GameMapCache {

	/**
	 * Register in {@link Redis} cache for a certain amount of time
	 * a byte array containing a {@link GameMap} file.
	 * @param id   of the GameMap
	 * @param file to be used
	 */
	void registerFile(String id, byte[] file);

	/**
	 * Register in {@link Redis} cache for a certain amount of time
	 * a byte array containing a {@link GameMap} file.
	 * @param id   of the GameMap
	 * @param file to be used
	 */
	void registerConfiguration(String id, byte[] file);

	/**
	 * Retrieve from cache an encoded version of the
	 * {@link GameMap} file.
	 * @param id of the GameMap
	 * @return byte encoded file to be used
	 * @throws GameControlException when the map is not cached
	 */
	byte[] getFile(String id) throws GameControlException;

	/**
	 * Retrieve from cache an encoded version of the
	 * {@link GameMap} configuration.
	 * @param id of the GameMap
	 * @return byte encoded file to be used
	 * @throws GameControlException when the map config is not cached
	 */
	byte[] getConfiguration(String id) throws GameControlException;

	/**
	 * Check if the map is actually cached in the database.
	 * @param id to check
	 * @return if cached or not
	 */
	boolean exists(String id);

}

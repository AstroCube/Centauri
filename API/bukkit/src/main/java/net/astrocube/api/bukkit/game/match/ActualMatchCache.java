package net.astrocube.api.bukkit.game.match;

import net.astrocube.api.bukkit.virtual.game.match.Match;

import java.util.Optional;

public interface ActualMatchCache {
	/**
	 * Obtains a registry from cache
	 * @param id to be placed
	 */
	Optional<Match> get(String id) throws Exception;

	/**
	 * Removes a user registry from cache
	 * @param id to be removed
	 */
	void remove(String id);

}

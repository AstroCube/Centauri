package net.astrocube.api.bukkit.game.match;

import net.astrocube.api.bukkit.virtual.game.match.Match;

import java.util.Optional;
import java.util.Set;

public interface ActualMatchProvider {

	/**
	 * Retrieve every match linked with an user.
	 * @param id of the user to be retrieved.
	 * @return match list.
	 * @throws Exception if can not communicate with backend
	 */
	Set<Match> getRegisteredMatches(String id) throws Exception;

}
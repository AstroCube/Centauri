package net.astrocube.api.bukkit.game.matchmaking;

import net.astrocube.api.bukkit.virtual.game.match.Match;

import java.util.Optional;
import java.util.Set;

/**
 * Select between an array of matches the best one to be
 * paired with a certain group depending some type of
 * conditions between them.
 */
public interface IdealMatchSelector {

	/**
	 * Sort the ideal match to be paired at the matchmaking.
	 * @param matches to be sorted
	 * @return ideal match to be used
	 */
	Optional<Match> sortAvailableMatches(Set<Match> matches);

}

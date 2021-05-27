package net.astrocube.api.bukkit.game.match;

import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.virtual.user.User;

import java.util.Optional;

public interface ActualMatchCache {

	/**
	 * Updates the subscription of the {@code assignable}
	 * responsible and involved users to the specified
	 * {@code match} match.
	 */
	void updateSubscription(Match match, MatchAssignable assignable) throws Exception;

	/** Gets the actual {@code user}'s match */
	Optional<Match> get(User user) throws Exception;

	/**
	 * Gets the actual user {@code id}'s match.
	 * It gets the {@link User} and then calls
	 * {@link ActualMatchCache#get(User)}
	 * @param id to be placed
	 */
	Optional<Match> get(String id) throws Exception;

	/**
	 * Clears the subscription of all the involved
	 * users in the given {@code match}
	 */
	void clearSubscriptions(Match match) throws Exception;

}

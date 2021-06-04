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

	/** Updates the subscription of the given user {@code id} */
	void updateSubscription(String id, MatchSubscription subscription) throws Exception;

	/**
	 * Gets the actual user {@code id}'s match.
	 * @param id to be placed
	 */
	Optional<Match> get(String id) throws Exception;

	/**
	 * Gets the actual user {@code id}'s match
	 * subscription
	 */
	Optional<MatchSubscription> getSubscription(String id) throws Exception;

	/**
	 * Clears the subscription of all the involved
	 * users in the given {@code match}
	 */
	void clearSubscriptions(Match match) throws Exception;

	/** Removes the match subscription of the given user {@code id} */
	void clearSubscription(String id) throws Exception;

}

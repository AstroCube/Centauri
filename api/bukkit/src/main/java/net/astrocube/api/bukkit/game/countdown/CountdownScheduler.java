package net.astrocube.api.bukkit.game.countdown;

import net.astrocube.api.bukkit.virtual.game.match.Match;

public interface CountdownScheduler {

	/**
	 * Schedule a countdown for a certain {@link Match}.
	 * @param match to be started
	 */
	default void scheduleMatchCountdown(Match match) {
		scheduleMatchCountdown(match, 30, false);
	}

	/**
	 * Schedule a countdown for a certain {@link Match}.
	 * @param match   to be started
	 * @param seconds before the match starts
	 * @param force   if the countdown was forced
	 */
	void scheduleMatchCountdown(Match match, int seconds, boolean force);

	/**
	 * Cancel match countdown until the minimum
	 * players requirement is met.
	 * @param match to be cancelled
	 */
	void cancelMatchCountdown(Match match);

	/**
	 * Check if match has an actual countdown running.
	 * @param match to be checked
	 * @return if the match has an active countdown
	 */
	boolean hasActiveCountdown(Match match);
}

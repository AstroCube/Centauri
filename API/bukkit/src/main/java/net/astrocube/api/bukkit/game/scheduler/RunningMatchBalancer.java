package net.astrocube.api.bukkit.game.scheduler;

public interface RunningMatchBalancer {

	/**
	 * Register a match at the balancer.
	 * @param id of the match to insert
	 */
	void registerMatch(String id);

	/**
	 * @return if has global/simultaneous capacity.
	 */
	boolean hasCapacity();

	/**
	 * @return actual running matches
	 */
	int getTotalMatches();

	/**
	 * @return if already completed full cycle.
	 */
	boolean isNeedingRestart();

	/**
	 * @return remaining capacity of the full cycle.
	 */
	int getRemainingCapacity();

	/**
	 * Removes a match of running actual matches.
	 * @param id of the match to release
	 */
	void releaseMatch(String id);

	/**
	 * @return simultaneous match capacity.
	 */
	int getSimultaneousCapacity();

	/**
	 * @return max capacity of overall matches before restart.
	 */
	int getMaxCapacity();


}

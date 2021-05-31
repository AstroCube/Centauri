package net.astrocube.api.bukkit.punishment.freeze;

import org.bukkit.entity.Player;

public interface FrozenUserProvider {

	/**
	 * Adds user to a frozen list
	 * @param player to be frozen
	 */
	void freeze(Player player);

	/**
	 * Removes user from frozen list
	 * @param player to unfreeze
	 */
	void unFreeze(Player player);

	/**
	 * Check if player is actually frozen
	 * @param player to check
	 * @return if player is frozen
	 */
	boolean isFrozen(Player player);

}

package net.astrocube.api.bukkit.game.spectator;

import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import org.bukkit.entity.Player;

public interface GhostEffectControl {

	/**
	 * Create the team
	 */

	void createTeam();

	/**
	 * Adds a player to a ghost {@link MatchDoc.Team}
	 * @param player to be applied the effect
	 */
	void addPlayer(Player player);

	/**
	 * Removes a player from ghost {@link MatchDoc.Team}
	 * @param player to be applied the effect
	 */
	void removePlayer(Player player);

	/**
	 * the Ghost
	 * @param player the player
	 */

	boolean isGhost(Player player);

}

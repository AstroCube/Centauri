package net.astrocube.api.bukkit.game.spectator;

import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import org.bukkit.entity.Player;

public interface GhostEffectControl {

	/**
	 * Adds a player to a ghost {@link MatchDoc.Team}
	 * @param match  to be added
	 * @param player to be applied the effect
	 */
	void addPlayer(String match, Player player);

	/**
	 * Removes a player from ghost {@link MatchDoc.Team}
	 * @param match  to be added
	 * @param player to be applied the effect
	 */
	void removePlayer(String match, Player player);

	/**
	 * Clears the {@link MatchDoc.Team} created for the match.
	 * @param match to be cleared
	 */
	void clearMatch(String match);

}

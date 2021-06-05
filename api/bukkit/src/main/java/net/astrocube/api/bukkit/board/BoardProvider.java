package net.astrocube.api.bukkit.board;

import org.bukkit.entity.Player;

import java.util.Optional;

/**
 * High-level {@link Board} manager, responsible
 * of holding and creating the scoreboards by player
 */
public interface BoardProvider {

	/**
	 * Creates a new scoreboard for the
	 * given {@code player} using the given
	 * {@code title}
	 */
	Board create(Player player, String title);

	/**
	 * Gets the {@link Board} linked to the
	 * given {@code player}
	 */
	Optional<Board> get(Player player);

	/**
	 * Removes the scoreboard linked to the
	 * given {@code player}
	 */
	void delete(Player player);

}

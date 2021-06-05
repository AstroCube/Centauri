package net.astrocube.api.bukkit.board;

import java.util.List;

/**
 * Interface that represents a handleable
 * player scoreboard
 */
public interface Board {

	/**
	 * Gets the current scoreboard title
	 */
	String getTitle();

	/**
	 * Sets the current scoreboard title
	 */
	void setTitle(String title);

	/**
	 * Gets the line at the specified {@code index}
	 */
	String getLine(int index);

	/**
	 * Sets the line at the specified {@code index}
	 */
	void set(int index, String line);

	/**
	 * Sets all the lines to this board
	 */
	void setLines(List<String> lines);

	/**
	 * Removes the entry at the specified {@code index}
	 */
	void remove(int index);

	/**
	 * Deletes this scoreboard
	 */
	void delete();

	/**
	 * Removes all the current existent
	 * entries in this scoreboard
	 */
	void clear();

}

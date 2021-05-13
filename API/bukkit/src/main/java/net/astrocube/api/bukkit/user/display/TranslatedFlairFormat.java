package net.astrocube.api.bukkit.user.display;

import org.bukkit.ChatColor;

public interface TranslatedFlairFormat {

	/**
	 * @return owner group of the translation
	 */
	String getId();

	/**
	 * @return color of the group
	 */
	ChatColor getColor();

	/**
	 * @return translated group name
	 */
	String getName();

	/**
	 * @return translated group prefix
	 */
	String getPrefix();

	/**
	 * @return translated join message
	 */
	String getJoinMessage();

	/**
	 * @return translated leave message
	 */
	String getLeaveMessage();

}

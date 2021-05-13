package net.astrocube.api.bukkit.lobby.hide;

public interface HideCompound {

	/**
	 * Check if compound will show user friends when active.
	 * @return boolean indicating property
	 */
	boolean friends();

	/**
	 * Check if compound will show staff when active.
	 * @return boolean indicating property
	 */
	boolean staff();

	/**
	 * Check if compound will show players with "lobby.hide.bypass" permission.
	 * @return boolean indicating property
	 */
	boolean permission();

}

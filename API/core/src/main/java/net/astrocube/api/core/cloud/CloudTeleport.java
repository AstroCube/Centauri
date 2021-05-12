package net.astrocube.api.core.cloud;

public interface CloudTeleport {

	/**
	 * Teleport a player to a certain server
	 * @param server to be teleported
	 * @param player to be teleported
	 */
	void teleportToServer(String server, String player);

	/**
	 * Teleport a player to a random server of the group
	 * @param group  to be queried
	 * @param player to be teleport
	 */
	void teleportToGroup(String group, String player);

	String getServerFromGroup(String group);

	/**
	 * Teleport player to actual server
	 * @param player to be teleported
	 */
	void teleportToActual(String player);

}

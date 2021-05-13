package net.astrocube.api.bukkit.teleport;

public interface ServerTeleportRetry {

	/**
	 * Attempt to teleport an user to a server.
	 * @param player     to teleport
	 * @param server     to teleport
	 * @param attempt    number
	 * @param maxAttempt numbers
	 */
	void attemptTeleport(String player, String server, int attempt, int maxAttempt);

	/**
	 * Attempt to teleport an user to a group.
	 * @param player     to teleport
	 * @param group      to teleport
	 * @param attempt    number
	 * @param maxAttempt numbers
	 */
	void attemptGroupTeleport(String player, String group, int attempt, int maxAttempt);

}

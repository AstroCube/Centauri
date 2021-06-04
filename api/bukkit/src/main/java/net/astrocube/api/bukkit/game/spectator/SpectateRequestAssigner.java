package net.astrocube.api.bukkit.game.spectator;

import javax.annotation.Nullable;

public interface SpectateRequestAssigner {

	/**
	 * Generates a request assignation for spectating
	 * @param gameMode  to spectate
	 * @param subMode   to spectate
	 * @param requester of spectate
	 */
	void assignRequest(String gameMode, @Nullable String subMode, String requester);

	/**
	 * Generates a request assignation for an specific match
	 * @param match     to spectate
	 * @param requester of spectate
	 * @param target    of spectate
	 */
	void assignRequestToPlayer(String match, String requester, String target);

}

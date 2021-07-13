package net.astrocube.api.bukkit.game.matchmaking;

import org.bukkit.entity.Player;

public interface MatchmakingRequester {

	/**
	 * Executes and request new game with mode and subMode
	 * @param player  player
	 * @param mode    id
	 * @param subMode id
	 */
	void execute(Player player, String mode, String subMode);


}

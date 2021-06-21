package net.astrocube.api.bukkit.game.lobby;

import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import org.bukkit.entity.Player;

public interface LobbyAssignerScoreboard {

	/**
	 * Assign or update the lobby scoreboard in state waiting
	 * @param player the player that assign scoreboard
	 * @param match the match game
	 */

	void assignLobbyScoreboard(Player player, Match match, SubGameMode subGameMode);

	/**
	 * Assign or update the lobby scoreboard in state starting
	 * @param player the player that assign scoreboard
	 * @param match the match game
	 * @param seconds the seconds left to start game
	 */

	void assignLobbyScoreboardStarting(Player player, Match match, int seconds, SubGameMode subGameMode);

}

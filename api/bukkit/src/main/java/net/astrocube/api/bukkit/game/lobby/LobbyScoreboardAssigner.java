package net.astrocube.api.bukkit.game.lobby;

import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import org.bukkit.entity.Player;

public interface LobbyScoreboardAssigner {

	/**
	 * Starts scheduling the lobby scoreboard update
	 * for the given {@code player} playing in the
	 * specified {@code match} and {@code subGameMode}
	 */
	void scheduleLobbyScoreboardUpdate(Player player, Match match, SubGameMode subGameMode);

	/**
	 * Stops updating the lobby scoreboard to the
	 * specified {@code player}
	 */
	void cancelLobbyScoreboardUpdate(Player player);

	/**
	 * Assign or update the lobby scoreboard in state starting
	 * @param player the player that assign scoreboard
	 * @param match the match game
	 * @param seconds the seconds left to start game
	 */
	void assignLobbyScoreboardStarting(Player player, Match match, int seconds, SubGameMode subGameMode);

}

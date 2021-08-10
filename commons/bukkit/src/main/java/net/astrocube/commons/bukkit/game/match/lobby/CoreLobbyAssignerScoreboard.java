package net.astrocube.commons.bukkit.game.match.lobby;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.board.Board;
import net.astrocube.api.bukkit.board.BoardProvider;
import net.astrocube.api.bukkit.game.lobby.LobbyScoreboardAssigner;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import net.astrocube.commons.bukkit.game.match.control.CoreMatchParticipantsProvider;
import org.bukkit.entity.Player;

public class CoreLobbyAssignerScoreboard implements LobbyScoreboardAssigner {

	@Inject private MessageHandler messageHandler;
	@Inject private BoardProvider boardProvider;

	@Override
	public void assignLobbyScoreboard(Player player, Match match, SubGameMode subGameMode) {
		Board board = findBoard(player, "game.scoreboard.waiting.title");

		board.setLines(messageHandler.replacingMany(
			player, "game.scoreboard.waiting.content",
			"%actual%", CoreMatchParticipantsProvider.getPendingIds(match).size(),
			"%max%", subGameMode.getMaxPlayers()
		));
	}

	@Override
	public void assignLobbyScoreboardStarting(Player player, Match match, int seconds, SubGameMode subGameMode) {
		Board board = findBoard(player, "game.scoreboard.starting.title");

		board.setLines(messageHandler.replacingMany(
			player, "game.scoreboard.starting.content",
			"%actual%", CoreMatchParticipantsProvider.getPendingIds(match).size(),
			"%max%", subGameMode.getMaxPlayers(),
			"%seconds%", seconds
		));
	}

	private Board findBoard(Player player, String titlePath) {
		return boardProvider.get(player)
			.orElseGet(() -> boardProvider.create(player, messageHandler.get(player, titlePath)));
	}

}

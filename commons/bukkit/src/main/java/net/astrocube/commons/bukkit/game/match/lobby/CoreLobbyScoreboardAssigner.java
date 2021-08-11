package net.astrocube.commons.bukkit.game.match.lobby;

import com.google.inject.Inject;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.board.Board;
import net.astrocube.api.bukkit.board.BoardProvider;
import net.astrocube.api.bukkit.game.lobby.LobbyScoreboardAssigner;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import net.astrocube.commons.bukkit.game.match.control.CoreMatchParticipantsProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CoreLobbyScoreboardAssigner implements LobbyScoreboardAssigner {

	private static final long UPDATE_RATE = 10L; // half second
	private final TObjectIntMap<UUID> tasks = new TObjectIntHashMap<>();

	@Inject private MessageHandler messageHandler;
	@Inject private BoardProvider boardProvider;
	@Inject	private Plugin plugin;

	@Override
	public void scheduleLobbyScoreboardUpdate(Player player, Match match, SubGameMode subGameMode) {

		UUID playerId = player.getUniqueId();

		if (tasks.containsKey(playerId)) {
			int previousTask = tasks.get(playerId);
			Bukkit.getScheduler().cancelTask(previousTask);
		}

		Board board = findBoard(player, "game.scoreboard.waiting.title");

		int taskId = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
			board.setLines(messageHandler.replacingMany(
				player, "game.scoreboard.waiting.content",
				"%actual%", CoreMatchParticipantsProvider.getPendingIds(match).size(),
				"%max%", subGameMode.getMaxPlayers()
			));
		}, 0L, UPDATE_RATE).getTaskId();

		tasks.put(playerId, taskId);
	}

	@Override
	public void cancelLobbyScoreboardUpdate(Player player) {
		UUID playerId = player.getUniqueId();
		if (tasks.containsKey(playerId)) {
			int taskId = tasks.remove(playerId);
			Bukkit.getScheduler().cancelTask(taskId);
		}
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

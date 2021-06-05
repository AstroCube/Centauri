package net.astrocube.commons.bukkit.board;

import net.astrocube.api.bukkit.board.Board;
import net.astrocube.api.bukkit.board.BoardProvider;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class PacketBoardProvider implements BoardProvider {

	private final Map<UUID, Board> boards = new HashMap<>();

	@Override
	public Board create(Player player, String title) {
		Board board = PacketBoard.create(player, title);
		Board deleted = boards.put(player.getUniqueId(), board);
		handleDeleted(deleted);
		return board;
	}

	@Override
	public Optional<Board> get(Player player) {
		return Optional.ofNullable(boards.get(player.getUniqueId()));
	}

	@Override
	public void delete(Player player) {
		handleDeleted(boards.remove(player.getUniqueId()));
	}

	private void handleDeleted(@Nullable Board board) {
		if (board != null) {
			board.delete();
		}
	}

}

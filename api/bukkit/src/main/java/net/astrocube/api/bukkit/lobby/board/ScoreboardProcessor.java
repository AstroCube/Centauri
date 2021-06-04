package net.astrocube.api.bukkit.lobby.board;

import org.bukkit.entity.Player;

public interface ScoreboardProcessor {

	void generateBoard(Player player) throws Exception;

}

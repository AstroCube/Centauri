package net.astrocube.lobby.task;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.lobby.board.ScoreboardProcessor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class ScoreboardUpdateTask implements Runnable {

	@Inject private ScoreboardProcessor scoreboardProcessor;

	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			try {
				scoreboardProcessor.generateBoard(player);
			} catch (Exception e) {
				Bukkit.getLogger().log(Level.WARNING, "Could not process user board", e);
			}
		}
	}

}

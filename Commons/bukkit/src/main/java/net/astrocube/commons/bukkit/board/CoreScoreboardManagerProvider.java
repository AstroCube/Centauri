package net.astrocube.commons.bukkit.board;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.board.ScoreboardManagerProvider;
import org.bukkit.plugin.Plugin;
import team.unnamed.uboard.ScoreboardManager;
import team.unnamed.uboard.SimpleScoreboardManager;

@Singleton
public class CoreScoreboardManagerProvider implements ScoreboardManagerProvider {

	private @Inject Plugin plugin;
	private ScoreboardManager manager;

	@Override
	public void setupManager() {
		manager = new SimpleScoreboardManager(plugin);
	}

	@Override
	public ScoreboardManager getScoreboard() {
		return manager;
	}

}

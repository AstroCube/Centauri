package net.astrocube.lobby.loader;

import com.google.inject.Inject;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.lobby.task.ScoreboardUpdateTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class TaskLoader implements Loader {

	@Inject private Plugin plugin;

	@Inject private ScoreboardUpdateTask scoreboardUpdateTask;

	@Override
	public void load() {
		if (plugin.getConfig().getBoolean("board")) {
			Bukkit.getScheduler().runTaskTimer(plugin, scoreboardUpdateTask, 0L, 50L);
		}
	}

}

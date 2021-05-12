package net.astrocube.commons.bukkit.game.scheduler;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.scheduler.RunningMatchBalancer;
import net.astrocube.api.core.cloud.CloudStatusProvider;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

@Singleton
public class CoreRunningMatchBalancer implements RunningMatchBalancer {

	private @Inject Plugin plugin;
	private @Inject CloudStatusProvider cloudStatusProvider;
	private final Set<String> matches = new HashSet<>();
	private int played = 0;

	@Override
	public void registerMatch(String id) {
		matches.add(id);
		played++;

		if (!hasCapacity() || isNeedingRestart()) {
			cloudStatusProvider.updateGameStatus(CloudStatusProvider.State.INGAME);
		}

	}

	@Override
	public boolean hasCapacity() {
		return matches.size() < getMaxCapacity();
	}

	@Override
	public int getTotalMatches() {
		return played;
	}

	@Override
	public boolean isNeedingRestart() {
		return getMaxCapacity() <= played;
	}

	@Override
	public int getRemainingCapacity() {
		return getSimultaneousCapacity() - matches.size();
	}

	@Override
	public void releaseMatch(String id) {
		matches.remove(id);

		if (hasCapacity() && !isNeedingRestart()) {
			cloudStatusProvider.updateGameStatus(CloudStatusProvider.State.WAITING);
		}

	}

	@Override
	public int getSimultaneousCapacity() {
		return plugin.getConfig().getInt("game.running", 2);
	}

	@Override
	public int getMaxCapacity() {
		return plugin.getConfig().getInt("game.total", 5);
	}

}

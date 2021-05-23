package net.astrocube.commons.bukkit.core.config;

import com.google.inject.Inject;
import net.astrocube.api.core.concurrent.ExecutorServiceProvider;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BukkitConcurrentProvider implements ExecutorServiceProvider {

	@Inject private Plugin plugin;
	private ExecutorService executorService;

	@Override
	public ExecutorService getRegisteredService() {
		if (executorService == null) {
			this.executorService = Executors.newFixedThreadPool(getConfiguredThreads());
		}
		return executorService;
	}

	@Override
	public int getConfiguredThreads() {
		return plugin.getConfig().getInt("api.threads", 2);
	}

}

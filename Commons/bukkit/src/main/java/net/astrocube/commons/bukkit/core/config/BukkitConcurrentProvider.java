package net.astrocube.commons.bukkit.core.config;

import net.astrocube.api.core.concurrent.ExecutorServiceProvider;
import org.bukkit.craftbukkit.libs.jline.internal.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BukkitConcurrentProvider implements ExecutorServiceProvider {

	private ExecutorService executorService;

	@Override
	public ExecutorService getRegisteredService() {
		if (executorService == null) this.executorService = Executors.newFixedThreadPool(getConfiguredThreads());
		return executorService;
	}

	@Override
	public int getConfiguredThreads() {
		return Configuration.getInteger("api.threads", 2);
	}

}

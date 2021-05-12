package net.astrocube.commons.bungee.configuration;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.core.concurrent.ExecutorServiceProvider;
import net.md_5.bungee.config.Configuration;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Singleton
public class BungeeConcurrentProvider implements ExecutorServiceProvider {

	private ExecutorService executorService;
	private @Inject PluginConfigurationHelper configurationHelper;

	@Override
	public ExecutorService getRegisteredService() {
		if (executorService == null) this.executorService = Executors.newFixedThreadPool(getConfiguredThreads());
		return executorService;
	}

	@Override
	public int getConfiguredThreads() {
		Optional<Configuration> configuration = Optional.ofNullable(configurationHelper.get());
		return configuration.map(value -> value.getInt("api.threads", 2)).orElse(2);
	}

}

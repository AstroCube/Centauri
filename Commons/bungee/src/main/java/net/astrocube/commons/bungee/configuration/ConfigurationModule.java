package net.astrocube.commons.bungee.configuration;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.concurrent.ExecutorServiceProvider;
import net.astrocube.api.core.http.config.HttpClientConfig;
import net.astrocube.api.core.http.config.HttpFactoryConfig;

public class ConfigurationModule extends ProtectedModule {

	@Override
	public void configure() {
		bind(ExecutorServiceProvider.class).to(BungeeConcurrentProvider.class);
		bind(HttpFactoryConfig.class).to(BungeeFactoryConfig.class);
		bind(HttpClientConfig.class).to(BungeeClientConfig.class);
	}

}

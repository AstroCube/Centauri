package net.astrocube.commons.bukkit.core;

import com.google.inject.Scopes;
import net.astrocube.inject.ProtectedModule;
import net.astrocube.api.core.concurrent.ExecutorServiceProvider;
import net.astrocube.api.core.http.config.HttpClientConfig;
import net.astrocube.api.core.http.config.HttpFactoryConfig;
import net.astrocube.commons.bukkit.core.config.BukkitClientConfig;
import net.astrocube.commons.bukkit.core.config.BukkitConcurrentProvider;
import net.astrocube.commons.bukkit.core.config.BukkitFactoryConfig;

public class BukkitConfigurationModule extends ProtectedModule {

	@Override
	public void configure() {
		bind(HttpFactoryConfig.class).to(BukkitFactoryConfig.class).in(Scopes.SINGLETON);
		bind(HttpClientConfig.class).to(BukkitClientConfig.class).in(Scopes.SINGLETON);
		bind(ExecutorServiceProvider.class).to(BukkitConcurrentProvider.class).in(Scopes.SINGLETON);
	}

}

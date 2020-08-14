package net.astrocube.commons.bukkit.core;

import com.google.inject.Scopes;
import net.astrocube.api.core.concurrent.ExecutorServiceProvider;
import net.astrocube.api.core.http.config.HttpClientConfig;
import net.astrocube.api.core.http.config.HttpFactoryConfig;
import net.astrocube.commons.bukkit.core.config.BukkitClientConfig;
import net.astrocube.commons.bukkit.core.config.BukkitConcurrentProvider;
import net.astrocube.commons.bukkit.core.config.BukkitFactoryConfig;
import net.astrocube.commons.core.inject.ProtectedModule;

public class BukkitConfigurationModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(HttpFactoryConfig.class).to(BukkitFactoryConfig.class);
        bind(HttpClientConfig.class).to(BukkitClientConfig.class);
        bind(ExecutorServiceProvider.class).to(BukkitConcurrentProvider.class).in(Scopes.SINGLETON);
        expose(HttpClientConfig.class);
        expose(HttpFactoryConfig.class);
    }

}

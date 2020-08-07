package net.astrocube.commons.bukkit.http;

import net.astrocube.api.core.http.config.HttpClientConfig;
import net.astrocube.api.core.http.config.HttpFactoryConfig;
import net.astrocube.commons.bukkit.http.config.BukkitClientConfig;
import net.astrocube.commons.bukkit.http.config.BukkitFactoryConfig;
import net.astrocube.commons.core.inject.ProtectedModule;

public class HttpModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(HttpFactoryConfig.class).to(BukkitFactoryConfig.class);
        bind(HttpClientConfig.class).to(BukkitClientConfig.class);
    }

}

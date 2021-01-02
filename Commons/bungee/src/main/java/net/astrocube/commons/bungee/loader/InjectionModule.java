package net.astrocube.commons.bungee.loader;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.commons.bungee.configuration.PluginConfigurationProvider;
import net.astrocube.commons.bungee.server.ServerModule;
import net.astrocube.commons.core.CommonsModule;
import net.md_5.bungee.config.Configuration;

public class InjectionModule extends ProtectedModule {

    @Override
    public void configure() {
        install(new CommonsModule());
        install(new LoaderModule());
        install(new ServerModule());
        bind(Configuration.class).toProvider(PluginConfigurationProvider.class);
    }

}

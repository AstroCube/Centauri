package net.astrocube.commons.bungee.loader;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.commons.bungee.configuration.ConfigurationModule;
import net.astrocube.commons.bungee.server.ServerModule;
import net.astrocube.commons.core.CommonsModule;

public class InjectionModule extends ProtectedModule {

    @Override
    public void configure() {
        install(new ConfigurationModule());
        install(new CommonsModule());
        install(new LoaderModule());
        install(new ServerModule());
    }

}

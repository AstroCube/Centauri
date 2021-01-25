package net.astrocube.commons.bungee.loader;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.commons.bungee.cloud.CloudModule;
import net.astrocube.commons.bungee.configuration.ConfigurationModule;
import net.astrocube.commons.bungee.server.ServerModule;
import net.astrocube.commons.core.CommonsModule;
import net.astrocube.commons.core.virtual.VirtualModule;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class InjectionModule extends ProtectedModule {

    @Override
    public void configure() {
        install(new ConfigurationModule());
        install(new CommonsModule());
        install(new CloudModule());
        install(new VirtualModule());
        install(new LoaderModule());
        install(new ServerModule());
    }

}

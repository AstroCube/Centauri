package net.astrocube.commons.bukkit.loader;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.commons.bukkit.core.BukkitConfigurationModule;
import net.astrocube.commons.bukkit.server.ServerModule;
import net.astrocube.commons.bukkit.session.BukkitSessionModule;
import net.astrocube.commons.core.CommonsModule;

public class InjectionLoaderModule extends ProtectedModule {

    @Override
    public void configure() {
        install(new CommonsModule());
        install(new LoaderModule());
        install(new ServerModule());
        install(new BukkitSessionModule());
        install(new BukkitConfigurationModule());
    }
}

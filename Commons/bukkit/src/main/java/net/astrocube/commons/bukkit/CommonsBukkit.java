package net.astrocube.commons.bukkit;

import com.google.inject.Inject;
import me.fixeddev.inject.ProtectedBinder;
import net.astrocube.api.core.service.delete.DeleteService;
import net.astrocube.api.core.virtual.server.Server;
import net.astrocube.commons.bukkit.loader.InjectionLoaderModule;
import org.bukkit.plugin.java.JavaPlugin;


public class CommonsBukkit extends JavaPlugin {

    private @Inject
    DeleteService<Server> deleteService;


    @Override
    public void onEnable() {

    }

    @Override
    public void configure(ProtectedBinder binder) {
        binder.install(new InjectionLoaderModule());
    }

}

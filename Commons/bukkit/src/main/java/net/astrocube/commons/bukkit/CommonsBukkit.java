package net.astrocube.commons.bukkit;

import com.google.inject.Inject;
import me.fixeddev.inject.ProtectedBinder;
import net.astrocube.api.core.service.create.CreateService;
import net.astrocube.api.core.virtual.server.Server;
import net.astrocube.api.core.virtual.server.ServerDoc;
import net.astrocube.commons.bukkit.loader.InjectionLoaderModule;
import org.bukkit.plugin.java.JavaPlugin;

public class CommonsBukkit extends JavaPlugin {

    private @Inject CreateService<Server, ServerDoc.Partial> createService;

    @Override
    public void onEnable() {
        this.createService.create(new ServerDoc.Identity() {
            @Override
            public String getSlug() {
                return "test-1";
            }

            @Override
            public ServerDoc.Type getServerType() {
                return ServerDoc.Type.SPECIAL;
            }

            @Override
            public String getCluster() {
                return "";
            }
        });
    }

    @Override
    public void configure(ProtectedBinder binder) {
        binder.install(new InjectionLoaderModule());
    }

}

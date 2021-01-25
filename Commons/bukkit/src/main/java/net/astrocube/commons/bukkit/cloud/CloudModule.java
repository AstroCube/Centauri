package net.astrocube.commons.bukkit.cloud;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.cloud.CloudStatusProvider;
import net.astrocube.api.core.cloud.CloudTeleport;
import net.astrocube.api.core.cloud.InstanceNameProvider;
import net.astrocube.commons.core.cloud.dummy.DummyCloudTeleport;
import net.astrocube.commons.core.cloud.dummy.DummyNameProvider;
import net.astrocube.commons.core.cloud.dummy.DummyStatusProvider;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class CloudModule extends ProtectedModule {

    @Override
    public void configure() {

        if (hasCloudDeploy()) {
            bind(InstanceNameProvider.class).to(CloudNameProvider.class);
            bind(CloudStatusProvider.class).to(CoreCloudStatusProvider.class);
            bind(CloudTeleport.class).to(CoreCloudTeleport.class);
        } else {
            bind(InstanceNameProvider.class).to(DummyNameProvider.class);
            bind(CloudStatusProvider.class).to(DummyStatusProvider.class);
            bind(CloudTeleport.class).to(DummyCloudTeleport.class);
        }

        expose(InstanceNameProvider.class);
        expose(CloudStatusProvider.class);
        expose(CloudTeleport.class);

    }

    public boolean hasCloudDeploy() {
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            if (plugin.getName().equalsIgnoreCase("TimoCloud")) {
                return true;
            }
        }
        return false;
    }

}

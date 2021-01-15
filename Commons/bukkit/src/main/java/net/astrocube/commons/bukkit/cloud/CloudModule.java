package net.astrocube.commons.bukkit.cloud;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.cloud.CloudStatusProvider;
import net.astrocube.api.core.cloud.CloudTeleport;
import net.astrocube.api.core.cloud.InstanceNameProvider;

public class CloudModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(InstanceNameProvider.class).to(BukkitNameProvider.class);
        bind(CloudStatusProvider.class).to(BukkitCloudStatusProvider.class);
        bind(CloudTeleport.class).to(BukkitCloudTeleport.class);
        expose(InstanceNameProvider.class);
        expose(CloudStatusProvider.class);
        expose(CloudTeleport.class);
    }

}

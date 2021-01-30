package net.astrocube.commons.bukkit.cloud;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.cloud.CloudStatusProvider;
import net.astrocube.api.core.cloud.CloudTeleport;
import net.astrocube.api.core.cloud.InstanceNameProvider;
import net.astrocube.commons.bukkit.cloud.dummy.DummyCloudTeleport;
import net.astrocube.commons.bukkit.cloud.dummy.DummyNameProvider;
import net.astrocube.commons.bukkit.cloud.dummy.DummyStatusProvider;

public class CloudModule extends ProtectedModule {

    @Override
    public void configure() {

        try {
            Class.forName("TimoCloudAPI");
            bind(InstanceNameProvider.class).to(CloudNameProvider.class);
            bind(CloudStatusProvider.class).to(CoreCloudStatusProvider.class);
            bind(CloudTeleport.class).to(CoreCloudTeleport.class);
        } catch (ClassNotFoundException e) {
            bind(InstanceNameProvider.class).to(DummyNameProvider.class);
            bind(CloudStatusProvider.class).to(DummyStatusProvider.class);
            bind(CloudTeleport.class).to(DummyCloudTeleport.class);
        }

        expose(InstanceNameProvider.class);
        expose(CloudStatusProvider.class);
        expose(CloudTeleport.class);

    }

}

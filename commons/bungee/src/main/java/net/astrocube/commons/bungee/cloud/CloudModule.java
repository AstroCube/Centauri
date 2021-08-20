package net.astrocube.commons.bungee.cloud;

import net.astrocube.inject.ProtectedModule;
import net.astrocube.api.core.cloud.CloudStatusProvider;
import net.astrocube.api.core.cloud.CloudTeleport;
import net.astrocube.api.core.cloud.InstanceNameProvider;
import net.astrocube.commons.core.cloud.CoreCloudTeleport;
import net.astrocube.commons.core.cloud.dummy.DummyCloudTeleport;
import net.astrocube.commons.core.cloud.dummy.DummyNameProvider;
import net.astrocube.commons.core.cloud.dummy.DummyStatusProvider;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class CloudModule extends ProtectedModule {

	@Override
	public void configure() {

		if (hasCloudHooked()) {
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

	public boolean hasCloudHooked() {
		for (Plugin plugin : ProxyServer.getInstance().getPluginManager().getPlugins()) {
			if (plugin.getDescription().getName().equalsIgnoreCase("TimoCloud")) {
				return true;
			}
		}
		return false;
	}

}

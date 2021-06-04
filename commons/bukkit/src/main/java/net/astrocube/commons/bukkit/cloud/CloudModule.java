package net.astrocube.commons.bukkit.cloud;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.cloud.*;
import net.astrocube.commons.core.cloud.CoreCloudInstanceProvider;
import net.astrocube.commons.core.cloud.CoreCloudTeleport;
import net.astrocube.commons.core.cloud.CoreModeConnectedProvider;
import net.astrocube.commons.core.cloud.dummy.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class CloudModule extends ProtectedModule {

	@Override
	public void configure() {

		if (hasCloudDeploy()) {
			bind(InstanceNameProvider.class).to(CloudNameProvider.class);
			bind(CloudStatusProvider.class).to(CoreCloudStatusProvider.class);
			bind(CloudTeleport.class).to(CoreCloudTeleport.class);
			bind(CloudInstanceProvider.class).to(CoreCloudInstanceProvider.class);
			bind(CloudModeConnectedProvider.class).to(CoreModeConnectedProvider.class);
		} else {
			bind(InstanceNameProvider.class).to(DummyNameProvider.class);
			bind(CloudStatusProvider.class).to(DummyStatusProvider.class);
			bind(CloudTeleport.class).to(DummyCloudTeleport.class);
			bind(CloudInstanceProvider.class).to(DummyCloudInstanceProvider.class);
			bind(CloudModeConnectedProvider.class).to(DummyModeConnectedProvider.class);
		}

		expose(InstanceNameProvider.class);
		expose(CloudStatusProvider.class);
		expose(CloudTeleport.class);
		expose(CloudModeConnectedProvider.class);
		expose(CloudInstanceProvider.class);

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

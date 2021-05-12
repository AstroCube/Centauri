package net.astrocube.commons.bungee.cloud;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.core.cloud.CloudStatusProvider;
import net.astrocube.api.core.cloud.InstanceNameProvider;
import net.md_5.bungee.api.ProxyServer;

@Singleton
public class CloudNameProvider implements InstanceNameProvider {

	private final CloudStatusProvider cloudStatusProvider;
	private boolean verified;
	private String name;

	@Inject
	public CloudNameProvider(CloudStatusProvider cloudStatusProvider) {
		this.cloudStatusProvider = cloudStatusProvider;
		this.verified = false;
		this.name = "";
	}

	@Override
	public String getName() {

		if (!verified) {

			this.verified = true;

			if (cloudStatusProvider.hasCloudHooked()) {
				this.name = TimoCloudAPI.getBungeeAPI().getThisProxy().getId();
			} else {
				this.name = ProxyServer.getInstance().getName();
			}

		}

		return name;
	}
}

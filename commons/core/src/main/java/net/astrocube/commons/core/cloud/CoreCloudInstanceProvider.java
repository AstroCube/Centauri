package net.astrocube.commons.core.cloud;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import cloud.timo.TimoCloud.api.objects.ServerGroupObject;
import cloud.timo.TimoCloud.api.objects.ServerObject;
import com.google.inject.Singleton;
import net.astrocube.api.core.cloud.CloudInstanceProvider;

import java.util.HashSet;
import java.util.Set;

@Singleton
public class CoreCloudInstanceProvider implements CloudInstanceProvider {

	@Override
	public Set<Instance> getGroupInstances(String name) {

		Set<Instance> instances = new HashSet<>();

		ServerGroupObject groupObject = TimoCloudAPI.getUniversalAPI().getServerGroup(name);

		if (groupObject == null) {
			return new HashSet<>();
		}

		for (ServerObject server : groupObject.getServers()) {
			instances.add(new Instance(
					server.getName(),
					server.getOnlinePlayerCount(),
					server.getMaxPlayerCount(),
					server.getOnlinePlayerCount() >= server.getMaxPlayerCount(),
					1
			));
		}

		return instances;
	}

	@Override
	public boolean isAvailable(String slug) {
		return TimoCloudAPI.getUniversalAPI().getServers().stream().anyMatch(s -> s.getId().equalsIgnoreCase(slug));
	}


}

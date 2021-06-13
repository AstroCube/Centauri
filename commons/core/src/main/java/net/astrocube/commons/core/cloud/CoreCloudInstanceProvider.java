package net.astrocube.commons.core.cloud;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import cloud.timo.TimoCloud.api.objects.ServerGroupObject;
import cloud.timo.TimoCloud.api.objects.ServerObject;
import com.google.inject.Singleton;
import net.astrocube.api.core.cloud.CloudInstanceProvider;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Singleton
public class CoreCloudInstanceProvider implements CloudInstanceProvider {

	private static final Pattern FIRST_NUMBER_PATTERN = Pattern.compile("[^0-9]*([0-9]+).*");

	@Override
	public Set<Instance> getGroupInstances(String name) {

		Set<Instance> instances = new HashSet<>();

		ServerGroupObject groupObject = TimoCloudAPI.getUniversalAPI().getServerGroup(name);

		if (groupObject == null) {
			return new HashSet<>();
		}

		for (ServerObject server : groupObject.getServers()) {

			// matches the first number in the server name
			Matcher matcher = FIRST_NUMBER_PATTERN.matcher(server.getName());
			int number = matcher.find() ? Integer.parseInt(matcher.group()) : 1;

			instances.add(new Instance(
					server.getName(),
					server.getOnlinePlayerCount(),
					server.getMaxPlayerCount(),
					server.getOnlinePlayerCount() >= server.getMaxPlayerCount(),
					number
			));
		}

		return instances;
	}

	@Override
	public boolean isAvailable(String slug) {
		return TimoCloudAPI.getUniversalAPI().getServers().stream().anyMatch(s -> s.getId().equalsIgnoreCase(slug));
	}


}

package net.astrocube.commons.bukkit.core.config;

import com.google.inject.Inject;
import net.astrocube.api.core.http.config.HttpFactoryConfig;
import org.bukkit.plugin.Plugin;

public class BukkitFactoryConfig implements HttpFactoryConfig {

	@Inject private Plugin plugin;

	@Override
	public int getConnectTimeout() {
		return plugin.getConfig().getInt("api.connect", 1200);
	}

	@Override
	public int getReadTimeout() {
		return plugin.getConfig().getInt("api.read", 1200);
	}

	@Override
	public int getRetryNumber() {
		return plugin.getConfig().getInt("api.retry", 3);
	}

}

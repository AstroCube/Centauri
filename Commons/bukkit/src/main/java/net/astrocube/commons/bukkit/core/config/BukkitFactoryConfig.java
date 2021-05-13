package net.astrocube.commons.bukkit.core.config;

import net.astrocube.api.core.http.config.HttpFactoryConfig;
import org.bukkit.craftbukkit.libs.jline.internal.Configuration;

public class BukkitFactoryConfig implements HttpFactoryConfig {

	@Override
	public int getConnectTimeout() {
		return Configuration.getInteger("api.connect", 1200);
	}

	@Override
	public int getReadTimeout() {
		return Configuration.getInteger("api.read", 1200);
	}

	@Override
	public int getRetryNumber() {
		return Configuration.getInteger("api.retry", 3);
	}

}

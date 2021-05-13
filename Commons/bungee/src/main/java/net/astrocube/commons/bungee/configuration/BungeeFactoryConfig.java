package net.astrocube.commons.bungee.configuration;

import com.google.inject.Inject;
import net.astrocube.api.core.http.config.HttpFactoryConfig;

public class BungeeFactoryConfig implements HttpFactoryConfig {

	private @Inject PluginConfigurationHelper configurationHelper;

	@Override
	public int getConnectTimeout() {
		return configurationHelper.get().getInt("api.connect", 1200);
	}

	@Override
	public int getReadTimeout() {
		return configurationHelper.get().getInt("api.timeout", 1200);
	}

	@Override
	public int getRetryNumber() {
		return configurationHelper.get().getInt("api.retry", 1200);
	}
}

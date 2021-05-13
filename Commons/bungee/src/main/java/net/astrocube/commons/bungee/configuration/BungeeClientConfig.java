package net.astrocube.commons.bungee.configuration;

import com.google.inject.Inject;
import net.astrocube.api.core.http.config.HttpClientConfig;

public class BungeeClientConfig implements HttpClientConfig {

	private @Inject PluginConfigurationHelper configurationHelper;

	@Override
	public String getBaseURL() {
		return configurationHelper.get().getString("api.route", "https://perseus.astrocube.net/api/");
	}

}

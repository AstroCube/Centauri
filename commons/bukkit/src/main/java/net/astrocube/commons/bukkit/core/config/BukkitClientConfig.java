package net.astrocube.commons.bukkit.core.config;

import com.google.inject.Inject;
import net.astrocube.api.core.http.config.HttpClientConfig;
import org.bukkit.plugin.Plugin;

public class BukkitClientConfig implements HttpClientConfig {

	@Inject private Plugin plugin;

	@Override
	public String getBaseURL() {
		return plugin.getConfig().getString("api.route", "https://perseus.astrocube.net/api/");
	}

}

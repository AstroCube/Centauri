package net.astrocube.commons.bukkit.http.config;

import net.astrocube.api.core.http.config.HttpClientConfig;
import org.bukkit.craftbukkit.libs.jline.internal.Configuration;

public class BukkitClientConfig implements HttpClientConfig {

    @Override
    public String getBaseURL() {
        return Configuration.getString("api.route", "https://perseus.astrocube.net");
    }

}

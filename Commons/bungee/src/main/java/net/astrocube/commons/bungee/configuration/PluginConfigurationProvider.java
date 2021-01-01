package net.astrocube.commons.bungee.configuration;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class PluginConfigurationProvider implements Provider<Configuration> {

    private @Inject Plugin plugin;

    @Override
    public Configuration get() {
        try {
            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), "config.yml"));
        } catch (IOException e) {
            return null;
        }
    }
}

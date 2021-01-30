package net.astrocube.commons.bungee.loader;

import com.google.common.io.ByteStreams;
import com.google.inject.Inject;
import net.astrocube.api.core.loader.Loader;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.*;

public class ConfigurationLoader implements Loader {

    private @Inject Plugin plugin;

    @Override
    public void load() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                try (InputStream is = plugin.getResourceAsStream("config.yml"); OutputStream os = new FileOutputStream(configFile)) {
                    ByteStreams.copy(is, os);
                }
            } catch (IOException e) {
                throw new RuntimeException("Unable to create configuration file", e);
            }
        }
    }

}

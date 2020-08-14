package net.astrocube.commons.bukkit.loader;

import com.google.inject.Inject;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.api.core.server.ServerStartResolver;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class ServerLoader implements Loader {

    private @Inject ServerStartResolver serverStartResolver;
    private @Inject Plugin plugin;

    @Override
    public void load() {
        plugin.getLogger().log(Level.INFO, "Starting server authorization");
        this.serverStartResolver.instantiateServer();
    }

}

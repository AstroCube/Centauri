package net.astrocube.commons.bukkit.loader;

import com.google.inject.Inject;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.api.core.server.ServerStartResolver;
import org.bukkit.Bukkit;

import java.util.logging.Level;

public class ServerLoader implements Loader {

    private @Inject ServerStartResolver serverStartResolver;

    @Override
    public void load() {
        Bukkit.getLogger().log(Level.INFO, "Starting server authorization");
        this.serverStartResolver.instantiateServer();
    }

}

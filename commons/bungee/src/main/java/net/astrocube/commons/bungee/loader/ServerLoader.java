package net.astrocube.commons.bungee.loader;

import com.google.inject.Inject;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.api.core.server.ServerStartResolver;
import net.md_5.bungee.api.plugin.Plugin;

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

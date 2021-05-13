package net.astrocube.commons.bungee;

import com.google.inject.Inject;
import me.fixeddev.inject.ProtectedBinder;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.api.core.server.ServerConnectionManager;
import net.astrocube.commons.bungee.loader.InjectionModule;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.logging.Level;

public class CommonsBungee extends Plugin {

	private @Inject Loader loader;
	private @Inject ServerConnectionManager serverConnectionManager;

	@Override
	public void onEnable() {
		loader.load();
	}

	@Override
	public void onDisable() {
		try {
			serverConnectionManager.endConnection();
		} catch (Exception e) {
			getLogger().log(Level.SEVERE, "Error disconnecting proxy", e);
		}
	}

	@Override
	public void configure(ProtectedBinder binder) {
		binder.install(new InjectionModule());
	}

}

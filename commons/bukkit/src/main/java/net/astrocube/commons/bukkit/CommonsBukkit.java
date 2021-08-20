package net.astrocube.commons.bukkit;

import com.google.inject.Inject;
import net.astrocube.inject.ProtectedBinder;
import net.astrocube.api.bukkit.server.ServerDisconnectHandler;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.commons.bukkit.loader.InjectionLoaderModule;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class CommonsBukkit extends JavaPlugin {

	private @Inject Loader loader;
	private @Inject ServerDisconnectHandler serverDisconnectHandler;

	@Override
	public void onEnable() {
		this.loader.load();
		copyDefaults();
	}

	@Override
	public void onDisable() {
		this.serverDisconnectHandler.execute();
	}

	@Override
	public void configure(ProtectedBinder binder) {
		binder.bind(FileConfiguration.class).toInstance(getConfig());
		binder.install(new InjectionLoaderModule());
	}

	public void copyDefaults() {
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
	}
}
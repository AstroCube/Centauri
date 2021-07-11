package net.astrocube.commons.bukkit;

import com.google.inject.Inject;
import me.fixeddev.inject.ProtectedBinder;
import net.astrocube.api.bukkit.server.ServerDisconnectHandler;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.commons.bukkit.loader.InjectionLoaderModule;
import net.astrocube.commons.bukkit.shutdown.JedisShutdownHook;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class CommonsBukkit extends JavaPlugin {

	private @Inject Loader loader;
	private @Inject ServerDisconnectHandler serverDisconnectHandler;
	private @Inject Redis redis;

	@Override
	public void onEnable() {
		this.loader.load();
		copyDefaults();
	}

	@Override
	public void onDisable() {
		this.serverDisconnectHandler.execute();
		Runtime.getRuntime().addShutdownHook(new JedisShutdownHook(redis));
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
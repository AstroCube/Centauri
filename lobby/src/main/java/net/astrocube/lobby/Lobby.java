package net.astrocube.lobby;

import com.google.inject.Inject;
import me.fixeddev.inject.ProtectedBinder;
import net.astrocube.lobby.loader.InjectionLoaderModule;
import net.astrocube.lobby.loader.LobbyLoader;
import org.bukkit.plugin.java.JavaPlugin;

public class Lobby extends JavaPlugin {

	private @Inject LobbyLoader loader;

	@Override
	public void onEnable() {
		this.loader.load();
		copyDefaults();
	}

	@Override
	public void configure(ProtectedBinder binder) {
		binder.bind(Lobby.class).toInstance(this);
		binder.install(new InjectionLoaderModule());
	}

	public void copyDefaults() {
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
	}

}

package net.astrocube.api.bukkit.server;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public interface ListenerLoader {

	/**
	 * Register events at plugin
	 */
	void registerEvents();

	default void registerEvent(Plugin plugin, Listener... listener) {

		for (Listener registered : listener) {
			plugin.getServer().getPluginManager().registerEvents(registered, plugin);
		}

	}

}

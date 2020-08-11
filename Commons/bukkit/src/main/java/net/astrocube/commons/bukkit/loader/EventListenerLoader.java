package net.astrocube.commons.bukkit.loader;

import com.google.inject.Inject;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.commons.bukkit.listener.UserJoinSessionListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class EventListenerLoader implements Loader {

    private @Inject UserJoinSessionListener userJoinSessionListener;
    private @Inject Plugin plugin;

    @Override
    public void load() {

        Bukkit.getLogger().log(Level.INFO, "Initializing event listeners");

        registerEvent(userJoinSessionListener);

    }

    private void registerEvent(Listener listener) {
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

}

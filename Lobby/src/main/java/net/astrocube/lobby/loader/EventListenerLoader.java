package net.astrocube.lobby.loader;

import com.google.inject.Inject;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.lobby.listener.hide.HideGadgetInteractListener;
import net.astrocube.lobby.listener.user.LobbyJoinListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class EventListenerLoader implements Loader {

    private @Inject HideGadgetInteractListener hideGadgetInteractListener;

    private @Inject LobbyJoinListener lobbyJoinListener;

    private @Inject Plugin plugin;

    @Override
    public void load() {
        plugin.getLogger().log(Level.INFO, "Initializing lobby event listeners");

        registerEvent(hideGadgetInteractListener);

        registerEvent(lobbyJoinListener);

    }

    private void registerEvent(Listener listener) {
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

}
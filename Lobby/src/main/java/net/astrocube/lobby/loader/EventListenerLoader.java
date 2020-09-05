package net.astrocube.lobby.loader;

import com.google.inject.Inject;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.lobby.listener.gadget.GameGadgetInteractListener;
import net.astrocube.lobby.listener.gadget.HideGadgetInteractListener;
import net.astrocube.lobby.listener.gadget.LobbySelectorGadgetInteractListener;
import net.astrocube.lobby.listener.user.LobbyJoinListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class EventListenerLoader implements Loader {

    private @Inject HideGadgetInteractListener hideGadgetInteractListener;
    private @Inject GameGadgetInteractListener gameGadgetInteractListener;
    private @Inject LobbySelectorGadgetInteractListener lobbySelectorGadgetInteractListener;

    private @Inject LobbyJoinListener lobbyJoinListener;

    private @Inject Plugin plugin;

    @Override
    public void load() {
        plugin.getLogger().log(Level.INFO, "Initializing lobby event listeners");

        registerEvent(hideGadgetInteractListener);
        registerEvent(gameGadgetInteractListener);
        registerEvent(lobbySelectorGadgetInteractListener);

        registerEvent(lobbyJoinListener);

    }

    private void registerEvent(Listener listener) {
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

}

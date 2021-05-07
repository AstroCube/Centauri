package net.astrocube.lobby.loader;

import com.google.inject.Inject;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.lobby.listener.environment.CropBreakingListener;
import net.astrocube.lobby.listener.environment.WeatherChangeListener;
import net.astrocube.lobby.listener.gadget.GameGadgetInteractListener;
import net.astrocube.lobby.listener.gadget.HideGadgetInteractListener;
import net.astrocube.lobby.listener.gadget.LobbySelectorGadgetInteractListener;
import net.astrocube.lobby.listener.gadget.ProfileGadgetInteractListener;
import net.astrocube.lobby.listener.user.LobbyJoinListener;
import net.astrocube.lobby.listener.user.PlayerDamageListener;
import net.astrocube.lobby.listener.user.PlayerQuitListener;
import net.astrocube.lobby.listener.user.UserBasicActionsListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class EventListenerLoader implements Loader {

    private @Inject HideGadgetInteractListener hideGadgetInteractListener;
    private @Inject GameGadgetInteractListener gameGadgetInteractListener;
    private @Inject LobbySelectorGadgetInteractListener lobbySelectorGadgetInteractListener;
    private @Inject ProfileGadgetInteractListener profileGadgetInteractListener;
    private @Inject PlayerDamageListener playerDamageListener;

    private @Inject LobbyJoinListener lobbyJoinListener;
    private @Inject PlayerQuitListener playerQuitListener;
    private @Inject UserBasicActionsListener userBasicActionsListener;

    private @Inject WeatherChangeListener weatherChangeListener;
    private @Inject CropBreakingListener cropBreakingListener;

    private @Inject Plugin plugin;

    @Override
    public void load() {
        plugin.getLogger().log(Level.INFO, "Initializing lobby event listeners");

        registerEvent(hideGadgetInteractListener);
        registerEvent(gameGadgetInteractListener);
        registerEvent(lobbySelectorGadgetInteractListener);
        registerEvent(profileGadgetInteractListener);
        registerEvent(playerDamageListener);

        registerEvent(lobbyJoinListener);
        registerEvent(playerQuitListener);
        registerEvent(userBasicActionsListener);

        registerEvent(weatherChangeListener);
        registerEvent(cropBreakingListener);

    }

    private void registerEvent(Listener listener) {
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

}

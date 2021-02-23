package net.astrocube.commons.bukkit.loader.listener.game;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.server.ListenerLoader;
import net.astrocube.commons.bukkit.listener.game.spectator.SpectateRequestListener;
import net.astrocube.commons.bukkit.listener.game.spectator.SpectatorAssignListener;
import org.bukkit.plugin.Plugin;

public class SpectatorListenerLoader implements ListenerLoader {

    private @Inject Plugin plugin;

    private @Inject SpectatorAssignListener spectatorAssignListener;
    private @Inject SpectateRequestListener spectateRequestListener;

    @Override
    public void registerEvents() {
        registerEvent(
                plugin,
                spectatorAssignListener,
                spectateRequestListener
        );
    }

}

package net.astrocube.commons.bukkit.loader.listener;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.server.ListenerLoader;
import net.astrocube.commons.bukkit.game.spectator.LobbyActionListener;
import net.astrocube.commons.bukkit.listener.game.session.LobbyUserDisconnectListener;
import net.astrocube.commons.bukkit.listener.game.spectator.LobbyReturnListener;
import org.bukkit.plugin.Plugin;

public class LobbyListenerLoader implements ListenerLoader {

    private @Inject Plugin plugin;

    private @Inject LobbyUserDisconnectListener lobbyUserDisconnectListener;
    private @Inject LobbyReturnListener lobbyReturnListener;
    private @Inject LobbyActionListener lobbyActionListener;

    @Override
    public void registerEvents() {
        registerEvent(
                plugin,
               lobbyUserDisconnectListener,
                lobbyReturnListener,
                lobbyActionListener
        );
    }

}

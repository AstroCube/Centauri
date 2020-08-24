package net.astrocube.lobby.listener.user;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.lobby.event.LobbyJoinEvent;
import net.astrocube.api.bukkit.lobby.hide.HideJoinProcessor;
import net.astrocube.api.bukkit.lobby.hotbar.LobbyHotbarProvider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LobbyJoinListener implements Listener {

    private @Inject HideJoinProcessor hideJoinProcessor;
    private @Inject LobbyHotbarProvider lobbyHotbarProvider;

    @EventHandler
    public void onLobbyJoin(LobbyJoinEvent event) {

        hideJoinProcessor.process(event.getUser());
        lobbyHotbarProvider.setup(event.getUser());

    }

}

package net.astrocube.lobby.listener.user;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.lobby.event.LobbyJoinEvent;
import net.astrocube.api.bukkit.lobby.hide.HideJoinProcessor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LobbyJoinListener implements Listener {

    private @Inject HideJoinProcessor hideJoinProcessor;

    @EventHandler
    public void onLobbyJoin(LobbyJoinEvent event) {

        hideJoinProcessor.process(event.getUser());

    }

}

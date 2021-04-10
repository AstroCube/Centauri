package net.astrocube.commons.bukkit.loader;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.server.ListenerLoader;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.commons.bukkit.loader.listener.*;
import net.astrocube.commons.bukkit.loader.listener.game.GameListenerLoader;
import net.astrocube.commons.bukkit.loader.listener.game.MatchListenerLoader;
import net.astrocube.commons.bukkit.loader.listener.game.MatchmakingListenerLoader;
import net.astrocube.commons.bukkit.loader.listener.game.SpectatorListenerLoader;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class EventListenerLoader implements Loader {

    private @Inject AuthenticationListenerLoader authenticationListenerLoader;
    private @Inject GameListenerLoader gameListenerLoader;
    private @Inject MatchmakingListenerLoader matchmakingListenerLoader;
    private @Inject FriendListenerLoader friendListenerLoader;
    private @Inject MatchListenerLoader matchListenerLoader;
    private @Inject UserListenerLoader userListenerLoader;
    private @Inject SpectatorListenerLoader spectatorListenerLoader;
    private @Inject LobbyListenerLoader lobbyListenerLoader;
    private @Inject InteractionListenerLoader interactionListenerLoader;
    private @Inject StaffListenerLoader staffListenerLoader;

    private @Inject Plugin plugin;

    @Override
    public void load() {

        plugin.getLogger().log(Level.INFO, "Initializing event listeners");

        if (plugin.getConfig().getBoolean("authentication.enabled")) {
            authenticationListenerLoader.registerEvents();
        }

        registerEvents(
                gameListenerLoader,
                matchListenerLoader,
                matchmakingListenerLoader,
                friendListenerLoader,
                userListenerLoader,
                lobbyListenerLoader,
                spectatorListenerLoader,
                interactionListenerLoader,
                staffListenerLoader
        );

    }

    private void registerEvents(ListenerLoader... loaders) {
        for (ListenerLoader loader : loaders) {
            loader.registerEvents();
        }
    }

}

package net.astrocube.commons.bukkit.loader;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.authentication.event.AuthenticationSuccessEvent;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.commons.bukkit.listener.authentication.AuthenticationInvalidListener;
import net.astrocube.commons.bukkit.listener.authentication.AuthenticationStartListener;
import net.astrocube.commons.bukkit.listener.authentication.AuthenticationSuccessListener;
import net.astrocube.commons.bukkit.listener.game.*;
import net.astrocube.commons.bukkit.listener.inventory.PlayerHotbarClickListener;
import net.astrocube.commons.bukkit.listener.user.UserJoinListener;
import net.astrocube.commons.bukkit.listener.user.UserLoginListener;
import net.astrocube.commons.bukkit.listener.user.UserPreLoginListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import team.unnamed.gui.MenuListeners;

import java.util.logging.Level;

public class EventListenerLoader implements Loader {

    private @Inject AuthenticationStartListener authenticationStartListener;
    private @Inject AuthenticationSuccessListener authenticationSuccessListener;
    private @Inject AuthenticationInvalidListener authenticationInvalidListener;

    private @Inject PlayerHotbarClickListener playerHotbarClickListener;

    private @Inject UserPreLoginListener userPreLoginListener;
    private @Inject UserLoginListener userLoginListener;
    private @Inject UserJoinListener userJoinListener;

    private @Inject GameModePairListener gameModePairListener;
    private @Inject GameTimerOutListener gameTimerOutListener;
    private @Inject MatchControlSanitizeListener matchControlSanitizeListener;
    private @Inject MatchmakingRequestListener matchmakingRequestListener;
    private @Inject LobbyUserJoinListener lobbyUserJoinListener;
    private @Inject MatchAssignationListener matchAssignationListener;

    private @Inject MenuListeners menuListeners;

    private @Inject Plugin plugin;

    @Override
    public void load() {

        plugin.getLogger().log(Level.INFO, "Initializing event listeners");

        registerEvent(authenticationStartListener);
        registerEvent(authenticationSuccessListener);
        registerEvent(authenticationInvalidListener);

        registerEvent(playerHotbarClickListener);

        registerEvent(userPreLoginListener);
        registerEvent(userLoginListener);
        registerEvent(userJoinListener);

        registerEvent(gameModePairListener);
        registerEvent(gameTimerOutListener);
        registerEvent(matchControlSanitizeListener);
        registerEvent(matchmakingRequestListener);
        registerEvent(lobbyUserJoinListener);
        registerEvent(matchAssignationListener);

        registerEvent(menuListeners);
    }

    private void registerEvent(Listener listener) {
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

}

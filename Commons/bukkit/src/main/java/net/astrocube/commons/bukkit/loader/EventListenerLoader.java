package net.astrocube.commons.bukkit.loader;

import com.google.inject.Inject;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.commons.bukkit.game.spectator.LobbyActionListener;
import net.astrocube.commons.bukkit.listener.authentication.AuthenticationInvalidListener;
import net.astrocube.commons.bukkit.listener.authentication.AuthenticationRestrictionListener;
import net.astrocube.commons.bukkit.listener.authentication.AuthenticationStartListener;
import net.astrocube.commons.bukkit.listener.authentication.AuthenticationSuccessListener;
import net.astrocube.commons.bukkit.listener.freeze.PlayerMoveListener;
import net.astrocube.commons.bukkit.listener.freeze.PlayerQuitListener;
import net.astrocube.commons.bukkit.listener.game.management.*;
import net.astrocube.commons.bukkit.listener.game.matchmaking.MatchmakingErrorListener;
import net.astrocube.commons.bukkit.listener.game.matchmaking.MatchmakingRequestListener;
import net.astrocube.commons.bukkit.listener.game.matchmaking.MatchmakingTimeoutListener;
import net.astrocube.commons.bukkit.listener.game.session.GameServerJoinListener;
import net.astrocube.commons.bukkit.listener.game.session.LobbyUserDisconnectListener;
import net.astrocube.commons.bukkit.listener.game.spectator.LobbyReturnListener;
import net.astrocube.commons.bukkit.listener.game.spectator.PlayerDamageListener;
import net.astrocube.commons.bukkit.listener.game.spectator.SpectatorAssignListener;
import net.astrocube.commons.bukkit.listener.inventory.PlayerHotbarClickListener;
import net.astrocube.commons.bukkit.listener.punishment.PunishmentIssueActionsListener;
import net.astrocube.commons.bukkit.listener.session.StaffSessionLogListener;
import net.astrocube.commons.bukkit.listener.user.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import team.unnamed.gui.core.GUIListeners;

import java.util.logging.Level;

public class EventListenerLoader implements Loader {

    private @Inject AuthenticationStartListener authenticationStartListener;
    private @Inject AuthenticationSuccessListener authenticationSuccessListener;
    private @Inject AuthenticationInvalidListener authenticationInvalidListener;
    private @Inject AuthenticationRestrictionListener authenticationRestrictionListener;

    private @Inject PlayerHotbarClickListener playerHotbarClickListener;

    private @Inject UserPreLoginListener userPreLoginListener;
    private @Inject UserLoginListener userLoginListener;
    private @Inject UserJoinListener userJoinListener;
    private @Inject UserDisconnectListener userDisconnectListener;
    private @Inject UserChatListener userChatListener;

    private @Inject GameModePairListener gameModePairListener;
    private @Inject GameTimerOutListener gameTimerOutListener;
    private @Inject MatchControlSanitizeListener matchControlSanitizeListener;
    private @Inject MatchmakingRequestListener matchmakingRequestListener;
    private @Inject GameServerJoinListener gameServerJoinListener;
    private @Inject LobbyUserDisconnectListener lobbyUserDisconnectListener;
    private @Inject MatchInvalidationListener matchInvalidationListener;
    private @Inject MatchmakingErrorListener matchmakingErrorListener;
    private @Inject MatchmakingTimeoutListener matchmakingTimeoutListener;
    private @Inject MatchAssignationListener matchAssignationListener;

    private @Inject MatchStartListener matchStartListener;
    private @Inject MatchFinishListener matchFinishListener;

    private @Inject SpectatorAssignListener spectatorAssignListener;
    private @Inject PlayerDamageListener playerDamageListener;
    private @Inject LobbyReturnListener lobbyReturnListener;
    private @Inject LobbyActionListener lobbyActionListener;

    private @Inject PlayerMoveListener playerMoveListener;
    private @Inject PlayerQuitListener playerQuitListener;

    private @Inject StaffSessionLogListener staffSessionLogListener;

    private @Inject PunishmentIssueActionsListener punishmentIssueActionsListener;

    private @Inject GUIListeners guiListeners;

    private @Inject Plugin plugin;

    @Override
    public void load() {

        plugin.getLogger().log(Level.INFO, "Initializing event listeners");

        if (plugin.getConfig().getBoolean("authentication.enabled")) {
            registerEvent(authenticationStartListener);
            registerEvent(authenticationSuccessListener);
            registerEvent(authenticationInvalidListener);
            registerEvent(authenticationRestrictionListener);
        }

        registerEvent(playerHotbarClickListener);

        registerEvent(userPreLoginListener);
        registerEvent(userLoginListener);
        registerEvent(userJoinListener);
        registerEvent(userDisconnectListener);

        registerEvent(gameModePairListener);
        registerEvent(gameTimerOutListener);
        registerEvent(matchControlSanitizeListener);
        registerEvent(matchmakingRequestListener);
        registerEvent(gameServerJoinListener);
        registerEvent(lobbyUserDisconnectListener);
        registerEvent(matchInvalidationListener);
        registerEvent(matchmakingTimeoutListener);
        registerEvent(matchAssignationListener);
        registerEvent(matchStartListener);
        registerEvent(matchFinishListener);
        registerEvent(matchmakingErrorListener);
        registerEvent(userChatListener);

        registerEvent(spectatorAssignListener);
        registerEvent(playerDamageListener);
        registerEvent(lobbyReturnListener);
        registerEvent(lobbyActionListener);

        registerEvent(staffSessionLogListener);

        registerEvent(punishmentIssueActionsListener);

        registerEvent(guiListeners);
        registerEvent(playerMoveListener);
        registerEvent(playerQuitListener);
    }

    private void registerEvent(Listener listener) {
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

}

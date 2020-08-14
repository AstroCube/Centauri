package net.astrocube.commons.bukkit.listener;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.session.SessionValidatorHandler;
import net.astrocube.api.core.session.SessionService;
import net.astrocube.api.core.virtual.session.SessionValidateDoc;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.bukkit.session.LoginEventSessionUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

@Singleton
public class UserPreLoginListener implements Listener {

    private @Inject SessionService sessionService;
    private @Inject SessionValidatorHandler sessionValidatorHandler;
    private @Inject Plugin plugin;

    @EventHandler
    public void onUserPreLogin(AsyncPlayerPreLoginEvent event) {
        try {
            SessionValidateDoc.Complete authorization = sessionService.authenticationCheckSync(
                    () -> LoginEventSessionUtil.retrieveRequestFromEvent(event)
            );
            sessionValidatorHandler.validateSession(event, authorization);
        } catch (Exception exception) {
            plugin.getLogger().log(Level.SEVERE, "There was an error while authenticating a user", exception);
            event.setKickMessage(ChatColor.RED + "AstroCube authentication servers are down, please try again later.");
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
        }
    }

}

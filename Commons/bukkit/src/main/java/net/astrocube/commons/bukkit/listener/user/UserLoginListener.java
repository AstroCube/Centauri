package net.astrocube.commons.bukkit.listener.user;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.session.SessionValidatorHandler;
import net.astrocube.api.core.virtual.session.SessionValidateDoc;
import net.astrocube.api.bukkit.authentication.event.AuthenticationStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.libs.jline.internal.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;

public class UserLoginListener implements Listener {

    private @Inject SessionValidatorHandler sessionValidatorHandler;
    private @Inject Plugin plugin;

    @EventHandler
    public void onUserLogin(PlayerLoginEvent event) {

        SessionValidateDoc.Complete validator =
                sessionValidatorHandler.getValidationPendingUser(event.getPlayer().getUniqueId());

        if (validator == null) {
            event.setKickMessage(ChatColor.RED + "There was an error validating your session, please try again.");
            event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
            return;
        }

        event.getPlayer().setDatabaseIdentifier(validator.getUser().getId());
        if (plugin.getConfig().getBoolean("authentication.enabled"))
            Bukkit.getPluginManager().callEvent(
                    new AuthenticationStartEvent(
                            validator.isRegistered(),
                            event.getPlayer(),
                            validator.getUser().getId()
                    )
            );

    }
}

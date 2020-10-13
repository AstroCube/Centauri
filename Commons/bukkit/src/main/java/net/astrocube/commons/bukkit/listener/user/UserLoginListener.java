package net.astrocube.commons.bukkit.listener.user;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.authentication.server.AuthenticationCooldown;
import net.astrocube.api.bukkit.session.SessionValidatorHandler;
import net.astrocube.api.core.virtual.session.SessionValidateDoc;
import net.astrocube.api.bukkit.authentication.event.AuthenticationStartEvent;
import net.astrocube.commons.core.utils.PrettyTimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;

public class UserLoginListener implements Listener {

    private @Inject SessionValidatorHandler sessionValidatorHandler;
    private @Inject MessageHandler<Player> messageHandler;
    private @Inject AuthenticationCooldown authenticationCooldown;
    private @Inject Plugin plugin;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onUserLogin(PlayerLoginEvent event) {

        SessionValidateDoc.Complete validator =
                sessionValidatorHandler.getValidationPendingUser(event.getPlayer().getUniqueId());

        if (validator == null) {
            event.setKickMessage(ChatColor.RED + "There was an error validating your session, please try again.");
            event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
            return;
        }

        event.getPlayer().setDatabaseIdentifier(validator.getUser().getId());

        if (plugin.getConfig().getBoolean("authentication.enabled")) {

            if (authenticationCooldown.hasCooldown(event.getPlayer().getDatabaseIdentifier())) {
                event.setKickMessage(
                        messageHandler.getMessage(
                                event.getPlayer(),
                                "cooldown-await"
                        ).replace(
                                "%%time%%",
                                PrettyTimeUtils.getHumanDate(
                                        authenticationCooldown.getRemainingTime(
                                                event.getPlayer().getDatabaseIdentifier()),
                                        "en"
                                )
                        )
                );
                event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
                return;
            }

            Bukkit.getPluginManager().callEvent(
                    new AuthenticationStartEvent(
                            validator.isRegistered(),
                            event.getPlayer(),
                            validator.getUser().getId()
                    )
            );
        }
    }
}

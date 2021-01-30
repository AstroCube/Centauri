package net.astrocube.commons.bukkit.listener.authentication;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.authentication.event.AuthenticationInvalidEvent;
import net.astrocube.api.bukkit.authentication.server.AuthenticationCooldown;
import net.astrocube.api.bukkit.authentication.server.CooldownKick;
import net.astrocube.api.core.authentication.AuthorizeException;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class AuthenticationInvalidListener implements Listener {

    private @Inject CooldownKick cooldownKick;
    private @Inject AuthenticationCooldown authenticationCooldown;
    private @Inject MessageHandler<Player> messageHandler;
    private @Inject FindService<User> findService;
    private @Inject Plugin plugin;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInvalidAuthentication(AuthenticationInvalidEvent event) {

        findService.find(event.getPlayer().getDatabaseIdentifier()).callback(response -> {
            try {

                if (!response.isSuccessful() || !response.getResponse().isPresent())
                    throw new AuthorizeException("Could not find requested user");

                User user = response.getResponse().get();

                cooldownKick.addTry(user);

                if (cooldownKick.getTries(user) > 2) {
                    authenticationCooldown.setCooldownLock(user.getId());
                    cooldownKick.checkAndKick(user, event.getPlayer());
                    cooldownKick.clearTries(user);
                }

            } catch (AuthorizeException exception) {
                plugin.getLogger().log(Level.WARNING, "Error authorizing player session", exception);
                event.getPlayer().kickPlayer(
                        messageHandler.get(event.getPlayer(), "authentication.unauthorized")
                                .replace("%%error%%", exception.getMessage())
                );
            }

        });


    }
}

package net.astrocube.commons.bungee.listener;

import com.google.inject.Inject;
import net.astrocube.api.core.session.SessionService;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.bungee.user.UserProvideHelper;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.util.Optional;
import java.util.logging.Level;

public class ServerDisconnectListener implements Listener {

    private @Inject UserProvideHelper userProvideHelper;
    private @Inject SessionService sessionService;
    private @Inject Plugin plugin;

    @EventHandler
    public void onServerDisconnect(PlayerDisconnectEvent event) {

        try {
            Optional<User> user = userProvideHelper.getUserByName(event.getPlayer().getName());

            if (user.isPresent()) {
                sessionService.serverDisconnect(user.get().getId());
            }

        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Error while obtaining user record", e);
        }

    }

}

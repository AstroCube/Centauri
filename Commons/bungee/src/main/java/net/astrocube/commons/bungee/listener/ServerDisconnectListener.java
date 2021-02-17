package net.astrocube.commons.bungee.listener;

import com.google.inject.Inject;
import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.message.Messenger;
import net.astrocube.api.core.session.SessionService;
import net.astrocube.api.core.session.SessionSwitchWrapper;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.bungee.user.UserProvideHelper;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Level;

public class ServerDisconnectListener implements Listener {

    private @Inject UserProvideHelper userProvideHelper;
    private @Inject SessionService sessionService;
    private @Inject Plugin plugin;
    private final Channel<SessionSwitchWrapper> channel;

    @Inject
    public ServerDisconnectListener(Messenger messenger) {
        channel = messenger.getChannel(SessionSwitchWrapper.class);
    }

    @EventHandler
    public void onServerDisconnect(PlayerDisconnectEvent event) {

        try {
            Optional<User> user = userProvideHelper.getUserByName(event.getPlayer().getName());

            if (user.isPresent()) {
                sessionService.serverDisconnect(user.get().getId());
                channel.sendMessage(new SessionSwitchWrapper() {
                    @Override
                    public User getUser() {
                        return user.get();
                    }

                    @Override
                    public boolean isConnecting() {
                        return false;
                    }
                }, new HashMap<>());
            }

        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Error while obtaining user record", e);
        }

    }

}

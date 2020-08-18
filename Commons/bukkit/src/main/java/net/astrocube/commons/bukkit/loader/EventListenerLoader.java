package net.astrocube.commons.bukkit.loader;

import com.google.inject.Inject;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.commons.bukkit.listener.authentication.AuthenticationStartListener;
import net.astrocube.commons.bukkit.listener.user.UserJoinListener;
import net.astrocube.commons.bukkit.listener.user.UserLoginListener;
import net.astrocube.commons.bukkit.listener.user.UserPreLoginListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class EventListenerLoader implements Loader {

    private @Inject AuthenticationStartListener authenticationStartListener;

    private @Inject UserPreLoginListener userPreLoginListener;
    private @Inject UserLoginListener userLoginListener;
    private @Inject UserJoinListener userJoinListener;

    private @Inject Plugin plugin;

    @Override
    public void load() {

        plugin.getLogger().log(Level.INFO, "Initializing event listeners");

        registerEvent(authenticationStartListener);

        registerEvent(userPreLoginListener);
        registerEvent(userLoginListener);
        registerEvent(userJoinListener);
    }

    private void registerEvent(Listener listener) {
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

}

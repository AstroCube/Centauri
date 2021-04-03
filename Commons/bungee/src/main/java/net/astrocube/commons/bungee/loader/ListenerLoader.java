package net.astrocube.commons.bungee.loader;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.commons.bungee.listener.PreLoginListener;
import net.astrocube.commons.bungee.listener.ServerConnectListener;
import net.astrocube.commons.bungee.listener.ServerDisconnectListener;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.logging.Level;


@Singleton
public class ListenerLoader implements Loader {

    private @Inject Plugin plugin;

    private @Inject ServerConnectListener serverConnectListener;
    private @Inject PreLoginListener preLoginListener;
    private @Inject ServerDisconnectListener serverDisconnectListener;

    @Override
    public void load() {

        plugin.getLogger().log(Level.INFO, "Initializing event listeners");

        registerEvent(serverConnectListener);
        registerEvent(preLoginListener);
        registerEvent(serverDisconnectListener);

    }

    private void registerEvent(Listener listener) {
        plugin.getProxy().getPluginManager().registerListener(plugin, listener);
    }

}


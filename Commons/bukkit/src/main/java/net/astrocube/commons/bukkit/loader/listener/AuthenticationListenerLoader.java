package net.astrocube.commons.bukkit.loader.listener;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.server.ListenerLoader;
import net.astrocube.commons.bukkit.listener.authentication.AuthenticationInvalidListener;
import net.astrocube.commons.bukkit.listener.authentication.AuthenticationRestrictionListener;
import net.astrocube.commons.bukkit.listener.authentication.AuthenticationStartListener;
import net.astrocube.commons.bukkit.listener.authentication.AuthenticationSuccessListener;
import org.bukkit.plugin.Plugin;

public class AuthenticationListenerLoader implements ListenerLoader {

    private @Inject Plugin plugin;

    private @Inject AuthenticationStartListener authenticationStartListener;
    private @Inject AuthenticationSuccessListener authenticationSuccessListener;
    private @Inject AuthenticationInvalidListener authenticationInvalidListener;
    private @Inject AuthenticationRestrictionListener authenticationRestrictionListener;

    @Override
    public void registerEvents() {
        registerEvent(
                plugin,
                authenticationStartListener,
                authenticationSuccessListener,
                authenticationInvalidListener,
                authenticationRestrictionListener
        );
    }

}

package net.astrocube.commons.bukkit.loader.listener;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.server.ListenerLoader;
import net.astrocube.commons.bukkit.listener.authentication.AuthenticationInvalidListener;
import net.astrocube.commons.bukkit.listener.authentication.AuthenticationRestrictionListener;
import net.astrocube.commons.bukkit.listener.authentication.AuthenticationStartListener;
import net.astrocube.commons.bukkit.listener.authentication.AuthenticationSuccessListener;
import net.astrocube.commons.bukkit.listener.friend.FriendAddListener;
import org.bukkit.plugin.Plugin;

public class FriendListenerLoader implements ListenerLoader {

    private @Inject Plugin plugin;
    private @Inject FriendAddListener friendAddListener;

    @Override
    public void registerEvents() {
        registerEvent(
                plugin,
                friendAddListener
        );
    }

}

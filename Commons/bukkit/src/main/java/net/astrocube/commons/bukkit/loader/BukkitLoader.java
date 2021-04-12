package net.astrocube.commons.bukkit.loader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.message.Messenger;
import net.astrocube.api.core.server.ServerAliveMessage;
import net.astrocube.commons.bukkit.authentication.AuthenticationUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import javax.inject.Named;
import java.util.HashMap;

public class BukkitLoader implements Loader {

    private @Inject @Named("server") Loader serverLoader;
    private @Inject @Named("events") Loader eventLoader;
    private @Inject @Named("commands") Loader commandLoader;
    private @Inject @Named("channels") Loader channelLoader;
    private @Inject Plugin plugin;
    private final Channel<ServerAliveMessage> serverAliveMessageChannel;

    @Inject
    public BukkitLoader(Messenger messenger) {
        this.serverAliveMessageChannel = messenger.getChannel(ServerAliveMessage.class);
    }

    @Override
    public void load() {
        this.serverLoader.load();
        this.eventLoader.load();
        this.commandLoader.load();
        this.channelLoader.load();

        if (plugin.getConfig().getBoolean("authentication.enabled")) {
            AuthenticationUtils.createSpaceEffect();
        }

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            try {
                serverAliveMessageChannel.sendMessage(
                        new ServerAliveMessage() {
                            @Override
                            public String getServer() {
                                return "";
                            }

                            @Override
                            public Action getAction() {
                                return Action.REQUEST;
                            }
                        },
                        new HashMap<>()
                );
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }, 20 * 5L);

    }
}
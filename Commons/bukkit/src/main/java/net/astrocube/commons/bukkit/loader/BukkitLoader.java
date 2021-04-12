package net.astrocube.commons.bukkit.loader;

import com.google.inject.Inject;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.commons.bukkit.authentication.AuthenticationUtils;
import org.bukkit.plugin.Plugin;

import javax.inject.Named;

public class BukkitLoader implements Loader {

    private @Inject @Named("server") Loader serverLoader;
    private @Inject @Named("events") Loader eventLoader;
    private @Inject @Named("commands") Loader commandLoader;
    private @Inject @Named("channels") Loader channelLoader;
    private @Inject Plugin plugin;


    @Override
    public void load() {
        this.serverLoader.load();
        this.eventLoader.load();
        this.commandLoader.load();
        this.channelLoader.load();

        if (plugin.getConfig().getBoolean("authentication.enabled")) {
            AuthenticationUtils.createSpaceEffect();
        }

    }
}
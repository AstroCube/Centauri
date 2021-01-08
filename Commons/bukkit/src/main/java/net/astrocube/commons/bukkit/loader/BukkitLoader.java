package net.astrocube.commons.bukkit.loader;

import com.google.inject.Inject;
import net.astrocube.api.core.loader.Loader;

import javax.inject.Named;

public class BukkitLoader implements Loader {

    private @Inject @Named("server") Loader serverLoader;
    private @Inject @Named("events") Loader eventLoader;
    private @Inject @Named("commands") Loader commandLoader;
    private @Inject @Named("channels") Loader channelLoader;

    @Override
    public void load() {
        this.serverLoader.load();
        this.eventLoader.load();
        this.commandLoader.load();
        this.channelLoader.load();
    }
}
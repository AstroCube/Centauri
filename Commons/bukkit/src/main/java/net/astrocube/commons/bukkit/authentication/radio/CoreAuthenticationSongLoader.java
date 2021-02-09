package net.astrocube.commons.bukkit.authentication.radio;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import net.astrocube.api.bukkit.authentication.radio.AuthenticationSongLoader;
import net.astrocube.apollo.api.Broadcaster;
import net.astrocube.apollo.api.Song;
import net.astrocube.apollo.api.internal.RadioBroadcaster;
import net.astrocube.apollo.api.util.NBSDecoder;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

@Singleton
public class CoreAuthenticationSongLoader implements AuthenticationSongLoader {

    private final Plugin plugin;
    private final Set<Song> songs;
    private @Getter Broadcaster broadcaster;

    @Inject
    public CoreAuthenticationSongLoader(Plugin plugin) {
        this.plugin = plugin;
        this.songs = new HashSet<>();
    }

    @Override
    public void generateBroadcaster() {

        this.broadcaster = new RadioBroadcaster(plugin);
        File file = new File("songs");

        if (!plugin.getConfig().getBoolean("authentication.broadcast")) {
            plugin.getLogger().log(Level.INFO, "Broadcaster disabled. Music will not be playing at register.");
            return;
        }

        if (!file.exists() || !file.isDirectory()) {
            plugin.getLogger().log(Level.INFO, "Creating empty folder for song broadcasting.");
            file.mkdir();
        }

        for (File songFile : file.listFiles()) {
            try {

                if (!FilenameUtils.getExtension(songFile.getName()).equalsIgnoreCase(".nbs")) {
                    throw new IOException("Invalid file format");
                }

                Song song = NBSDecoder.decode(songFile);
                songs.add(song);
                broadcaster.queue(song);

            } catch (IOException e) {
                plugin.getLogger().log(Level.WARNING, "Could not decode {0} file. ({1})", new String[]{songFile.getName(), e.getMessage()});
            }
        }

        if (broadcaster.getQueue().size() > 0) {
            broadcaster.setLoop(true);
        }

    }

}

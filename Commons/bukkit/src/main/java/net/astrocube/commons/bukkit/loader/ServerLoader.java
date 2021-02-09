package net.astrocube.commons.bukkit.loader;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.authentication.radio.AuthenticationSongLoader;
import net.astrocube.api.bukkit.board.ScoreboardManagerProvider;
import net.astrocube.api.bukkit.punishment.PresetPunishmentCache;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.api.core.server.ServerStartResolver;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class ServerLoader implements Loader {

    private @Inject ServerStartResolver serverStartResolver;
    private @Inject Plugin plugin;
    private @Inject AuthenticationSongLoader authenticationSongLoader;
    private @Inject PresetPunishmentCache presetPunishmentCache;
    private @Inject ScoreboardManagerProvider scoreboardManagerProvider;

    @Override
    public void load() {
        plugin.getLogger().log(Level.INFO, "Starting server authorization");
        this.serverStartResolver.instantiateServer();

        if (plugin.getConfig().getBoolean("authentication.enabled")) {
            authenticationSongLoader.generateBroadcaster();
        }

        presetPunishmentCache.generateCache();
        scoreboardManagerProvider.setupManager();

    }

}

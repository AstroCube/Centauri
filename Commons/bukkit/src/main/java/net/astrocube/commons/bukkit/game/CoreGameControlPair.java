package net.astrocube.commons.bukkit.game;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import net.astrocube.api.bukkit.game.GameControlPair;
import net.astrocube.api.bukkit.game.event.control.MatchControlStartEvent;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import net.astrocube.api.core.virtual.server.ServerDoc;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

@Singleton
public class CoreGameControlPair implements GameControlPair {

    private final Plugin plugin;
    private ServerDoc.Type type;
    private int stopSchedule;
    private @Getter boolean paired;

    @Inject
    CoreGameControlPair(Plugin plugin) {
        this.plugin = plugin;
        this.type = ServerDoc.Type.SPECIAL;
        this.stopSchedule = -1;
        this.paired = false;

        try {
            this.type = ServerDoc.Type.valueOf(plugin.getConfig().getString("server.type"));
        } catch (Exception ignore) {}
    }

    @Override
    public void validatePair(GameMode gameMode, SubGameMode subGameMode) throws GameControlException {

        if (type != ServerDoc.Type.GAME) {
            throw new GameControlException("Server not in GAME mode");
        }

        FileConfiguration config = plugin.getConfig();
        plugin.getLogger().log(Level.INFO, "Attempting to pair provided GameMode");

        if (
                !gameMode.getId().equalsIgnoreCase(config.getString("game.mode")) ||
                !subGameMode.getId().equalsIgnoreCase(config.getString("game.subMode"))
        ) {
            throw new GameControlException("Provided mode does not correspond with configuration");
        }

        if (stopSchedule == -1) {
            throw new GameControlException("Scheduled stop was not correctly written before or pairing never was enabled");
        }

        Bukkit.getScheduler().cancelTask(stopSchedule);

        plugin.getLogger().log(Level.INFO,
                "Successfully paired GameMode {0} (ID: {1}) and SubGameMode {2} (ID: {3})",
                new String[]{gameMode.getName(), gameMode.getId(), subGameMode.getName(), subGameMode.getId()}
        );

        this.paired = true;

        Bukkit.getPluginManager().callEvent(new MatchControlStartEvent(gameMode, subGameMode));

    }

    @Override
    public void enablePairing() throws GameControlException {

        if (type != ServerDoc.Type.GAME) {
            throw new GameControlException("Server not in GAME mode");
        }

        this.stopSchedule = Bukkit.getScheduler().runTaskLater(
                plugin,
                () -> {
                    plugin.getLogger().log(Level.SEVERE, "No game was paired during grace time, shutting down server.");
                    Bukkit.shutdown();
                },
                600L
        ).getTaskId();

    }

}

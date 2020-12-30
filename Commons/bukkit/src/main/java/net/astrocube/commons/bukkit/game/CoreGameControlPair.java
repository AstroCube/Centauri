package net.astrocube.commons.bukkit.game;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import net.astrocube.api.bukkit.game.GameControlPair;
import net.astrocube.api.bukkit.game.event.game.GameModePairEvent;
import net.astrocube.api.bukkit.game.event.game.GamePairEnableEvent;
import net.astrocube.api.bukkit.game.event.match.MatchControlSanitizeEvent;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import net.astrocube.api.core.virtual.server.ServerDoc;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.logging.Level;

@Singleton
public class CoreGameControlPair implements GameControlPair {

    private final Plugin plugin;
    private final FindService<GameMode> findService;
    private ServerDoc.Type type;
    private int stopSchedule;
    private int repeatingSchedule;
    private @Getter boolean paired;

    @Inject
    CoreGameControlPair(Plugin plugin, FindService<GameMode> findService) {
        this.plugin = plugin;
        this.findService = findService;
        this.type = ServerDoc.Type.SPECIAL;
        this.stopSchedule = -1;
        this.repeatingSchedule = -1;
        this.paired = false;

        try {
            this.type = ServerDoc.Type.valueOf(plugin.getConfig().getString("server.type"));
        } catch (Exception ignore) {}
    }

    @Override
    public void validatePair(String gameMode, String subGameMode) throws Exception {

        if (type != ServerDoc.Type.GAME) {
            throw new GameControlException("Server not in GAME mode");
        }

        FileConfiguration config = plugin.getConfig();
        plugin.getLogger().log(Level.INFO, "Attempting to pair provided GameMode");

        GameMode mode = findService.findSync(gameMode);

        if (mode.getSubTypes() == null) {
            plugin.getLogger().log(Level.SEVERE, "The requested GameMode does not have any SubMode");
            return;
        }

        Optional<SubGameMode> subMode = mode.getSubTypes().stream()
                .filter(g -> g.getId().equalsIgnoreCase(subGameMode))
                .findFirst();

        if (!subMode.isPresent()) {
            plugin.getLogger().log(Level.SEVERE, "The requested GameMode was not found");
            return;
        }


        if (
                !mode.getId().equalsIgnoreCase(config.getString("game.mode")) ||
                !subMode.get().getId().equalsIgnoreCase(config.getString("game.subMode"))
        ) {
            throw new GameControlException("Provided mode does not correspond with configuration");
        }

        if (stopSchedule == -1) {
            throw new GameControlException("Scheduled stop was not correctly written before or pairing never was enabled");
        }

        Bukkit.getScheduler().cancelTask(stopSchedule);
        Bukkit.getScheduler().cancelTask(repeatingSchedule);

        plugin.getLogger().log(Level.INFO,
                "Successfully paired GameMode {0} (ID: {1}) and SubGameMode {2} (ID: {3})",
                new String[]{mode.getName(), mode.getId(), subMode.get().getName(), subMode.get().getId()}
        );

        this.paired = true;

        Bukkit.getPluginManager().callEvent(new MatchControlSanitizeEvent(mode, subMode.get()));

    }

    @Override
    public void enablePairing() throws GameControlException {

        if (type != ServerDoc.Type.GAME) {
            throw new GameControlException("Server not in GAME mode");
        }

        plugin.getLogger().log(Level.INFO, "Starting game pairing, the server will shut down if no game can be paired during grace time..");

        this.repeatingSchedule = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> Bukkit.getPluginManager().callEvent(new GamePairEnableEvent()), 40L, 100L).getTaskId();

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

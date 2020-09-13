package net.astrocube.commons.bukkit.game;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.GameControlPair;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import net.astrocube.api.core.virtual.server.ServerDoc;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

@Singleton
public class CoreGameControlPair implements GameControlPair {

    private final Plugin plugin;
    private ServerDoc.Type type;

    @Inject
    CoreGameControlPair(Plugin plugin) {

        this.plugin = plugin;
        this.type = ServerDoc.Type.SPECIAL;

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

        plugin.getLogger().log(Level.INFO,
                "Successfully paired GameMode {0} (ID: {1}) and SubGameMode {2} (ID: {3})",
                new String[]{gameMode.getName(), gameMode.getId(), subGameMode.getName(), subGameMode.getId()}
        );

    }

}

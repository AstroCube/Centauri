package net.astrocube.commons.bukkit.listener.game;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.GameControlPair;
import net.astrocube.api.bukkit.game.event.game.GameModePairEvent;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class GameModePairListener implements Listener {

    private @Inject GameControlPair gameControlPair;
    private @Inject Plugin plugin;

    @EventHandler
    public void onGameModePair(GameModePairEvent event) {

        try {
            gameControlPair.validatePair(event.getGameMode(), event.getSubGameMode());
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Error while pairing GameMode", e);
            Bukkit.shutdown();
        }

    }

}

package net.astrocube.commons.bukkit.game.spectator;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.spectator.SpectatorLobbyTeleporter;
import net.astrocube.api.bukkit.teleport.ServerTeleportRetry;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.core.cloud.CloudTeleport;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class CoreSpectatorLobbyTeleporter implements SpectatorLobbyTeleporter {

    private @Inject MessageHandler messageHandler;
    private @Inject Plugin plugin;
    private @Inject ServerTeleportRetry serverTeleportRetry;
    private final Map<String, Integer> scheduledTeleports = new HashMap<>();

    @Override
    public void scheduleTeleport(Player player) {

        messageHandler.sendIn(player, AlertModes.MUTED, "game.return.schedule");

        scheduledTeleports.put(player.getDatabaseIdentifier(),
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    serverTeleportRetry.attemptGroupTeleport(
                            player.getName(),
                            plugin.getConfig().getString("server.fallback", "main-lobby"),
                            1,
                            3
                    );
                    scheduledTeleports.remove(player.getDatabaseIdentifier());
                }, 20*5L).getTaskId()
        );

    }

    @Override
    public void cancelTeleport(Player player) {
        messageHandler.sendIn(player, AlertModes.MUTED, "game.return.cancel");

        if (hasScheduledTeleport(player)) {
            scheduledTeleports.get(player.getDatabaseIdentifier());
            scheduledTeleports.remove(player.getDatabaseIdentifier());
        }
    }

    @Override
    public boolean hasScheduledTeleport(Player player) {
        return scheduledTeleports.get(player.getDatabaseIdentifier()) != null;
    }

}

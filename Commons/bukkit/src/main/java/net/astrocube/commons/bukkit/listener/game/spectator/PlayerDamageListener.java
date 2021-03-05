package net.astrocube.commons.bukkit.listener.game.spectator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.game.match.UserMatchJoiner;
import net.astrocube.api.bukkit.game.spectator.SpectatorSessionManager;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.logging.Level;

public class PlayerDamageListener implements Listener {

    private @Inject ActualMatchCache actualMatchCache;
    private @Inject SpectatorSessionManager spectatorSessionManager;
    private @Inject Plugin plugin;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDamage(EntityDamageEvent event) {

        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();

        try {
            Optional<Match> matchOptional = actualMatchCache.get(player.getDatabaseIdentifier());

            matchOptional.ifPresent(match -> {
                try {

                    if (UserMatchJoiner.checkOrigin(player.getDatabaseIdentifier(), match) ==
                            UserMatchJoiner.Origin.SPECTATING) {
                        event.setCancelled(true);
                    }

                    if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
                        spectatorSessionManager.provideFunctions(player, match);
                    }

                } catch (GameControlException | JsonProcessingException ignore) {}
            });

        } catch (Exception e) {
            plugin.getLogger().log(Level.WARNING, "Error while checking actual match.");
        }

    }

}

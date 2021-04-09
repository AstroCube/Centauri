package net.astrocube.commons.bukkit.listener.game.spectator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.map.GameMapCache;
import net.astrocube.api.bukkit.game.map.MapConfigurationProvider;
import net.astrocube.api.bukkit.game.map.configuration.CoordinatePoint;
import net.astrocube.api.bukkit.game.map.configuration.GameMapConfiguration;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.game.match.UserMatchJoiner;
import net.astrocube.api.bukkit.game.spectator.SpectatorSessionManager;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.logging.Level;

public class PlayerDamageListener implements Listener {

    private @Inject ActualMatchCache actualMatchCache;
    private @Inject SpectatorSessionManager spectatorSessionManager;
    private @Inject Plugin plugin;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDamage(EntityDamageByEntityEvent event) {

        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();

        try {
            Optional<Match> matchOptional = actualMatchCache.get(player.getDatabaseIdentifier());

            matchOptional.ifPresent(match -> {
                try {

                    if (UserMatchJoiner.checkOrigin(damager.getDatabaseIdentifier(), match)
                            == UserMatchJoiner.Origin.SPECTATING) {
                        event.setCancelled(true);
                        return;
                    }

                } catch (GameControlException ignore) {}
            });

        } catch (Exception e) {
            plugin.getLogger().log(Level.WARNING, "Error while checking actual match.");
        }

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageEvent event) {


        Player player = (Player) event.getEntity();

        try {
            Optional<Match> matchOptional = actualMatchCache.get(player.getDatabaseIdentifier());

            matchOptional.ifPresent(match -> {
                try {

                    if (UserMatchJoiner.checkOrigin(player.getDatabaseIdentifier(), match) ==
                            UserMatchJoiner.Origin.SPECTATING) {

                        if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
                            spectatorSessionManager.provideFunctions(player, match);
                        }

                        event.setCancelled(true);
                    }

                } catch (GameControlException | JsonProcessingException ignore) {}
            });

        } catch (Exception e) {
            plugin.getLogger().log(Level.WARNING, "Error while checking actual match.");
        }

    }

}

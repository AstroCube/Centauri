package net.astrocube.commons.bukkit.listener.game.spectator;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.match.ActualMatchProvider;
import net.astrocube.api.bukkit.game.match.UserMatchJoiner;
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

    private @Inject ActualMatchProvider actualMatchProvider;
    private @Inject Plugin plugin;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDamage(EntityDamageEvent event) {

        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();

        try {
            Optional<Match> matchOptional = actualMatchProvider.provide(player.getDatabaseIdentifier());

            matchOptional.ifPresent(match -> {
                try {

                    if (UserMatchJoiner.checkOrigin(player.getDatabaseIdentifier(), match) ==
                            UserMatchJoiner.Origin.SPECTATING) {
                        event.setCancelled(true);
                    }

                } catch (GameControlException ignore) {}
            });

        } catch (Exception e) {
            plugin.getLogger().log(Level.WARNING, "Error while checking actual match.");
        }

    }

}

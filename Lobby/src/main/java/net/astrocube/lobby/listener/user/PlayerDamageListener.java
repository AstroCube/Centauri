package net.astrocube.lobby.listener.user;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.lobby.nametag.LobbyNametagHandler;
import net.astrocube.api.bukkit.lobby.selector.npc.SelectorRegistry;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

public class PlayerDamageListener implements Listener {

    private @Inject Plugin plugin;

    @EventHandler
    public void onPlayerQuit(EntityDamageEvent event) {

        Player player = (Player) event.getEntity();

        if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {

            player.teleport(
                    new Location(
                            Bukkit.getWorlds().get(0),
                            plugin.getConfig().getInt("spawn.x", 0),
                            plugin.getConfig().getInt("spawn.y", 0),
                            plugin.getConfig().getInt("spawn.z", 0),
                            plugin.getConfig().getInt("spawn.yaw", 0),
                            plugin.getConfig().getInt("spawn.pitch", 0)
                    )
            );

            event.setCancelled(true);

        }


    }

}
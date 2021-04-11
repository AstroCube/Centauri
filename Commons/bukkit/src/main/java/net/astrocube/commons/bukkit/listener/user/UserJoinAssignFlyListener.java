package net.astrocube.commons.bukkit.listener.user;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UserJoinAssignFlyListener implements Listener {

    @EventHandler
    public void onUserJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission("commons.fly")) {
            player.setAllowFlight(true);
        }

    }

}

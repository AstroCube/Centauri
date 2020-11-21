package net.astrocube.commons.bukkit.listener.teleport;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.teleport.CrossTeleportExchanger;
import net.astrocube.api.bukkit.teleport.event.TeleportRequestEvent;
import net.astrocube.api.bukkit.teleport.request.TeleportRequest;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TeleportRequestListener implements Listener {

    private @Inject CrossTeleportExchanger crossTeleportExchanger;

    @EventHandler
    public void onTeleportRequest(TeleportRequestEvent event) {

        TeleportRequest request = event.getRequest();
        Player receiver = Bukkit.getPlayer(request.getReceiver().getUsername());

        if (receiver == null) {
            return;
        }

        if (!request.getServer().isPresent()) {

            if (!request.getLocation().isPresent()) {
                return;
            }

            receiver.teleport(request.getLocation().get());
        }

        crossTeleportExchanger.schedule(request);

    }
}

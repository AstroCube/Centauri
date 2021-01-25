package net.astrocube.commons.bukkit.listener.game.management;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.event.match.MatchAssignationEvent;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.cloud.CloudTeleport;
import net.astrocube.api.core.cloud.InstanceNameProvider;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MatchAssignationListener implements Listener {

    private @Inject FindService<User> userFindService;
    private @Inject InstanceNameProvider instanceNameProvider;
    private @Inject CloudTeleport cloudTeleport;

    @EventHandler
    public void onMatchAssignation(MatchAssignationEvent event) {

        if (instanceNameProvider.getName().equalsIgnoreCase(event.getAssignation().getServer())) {
            userFindService.find(event.getAssignation().getUser()).callback(userResponse -> {
                if (!userResponse.isSuccessful() || !userResponse.getResponse().isPresent()) {
                    // TODO: Send message in order to fail pairing
                }
                cloudTeleport.teleportToActual(userResponse.getResponse().get().getUsername());
            });
        }

    }

}

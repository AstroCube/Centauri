package net.astrocube.commons.bukkit.listener.game;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.event.MatchAssignationEvent;
import net.astrocube.api.bukkit.game.match.control.MatchJoinAuthorization;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MatchAssignationListener implements Listener {

    private @Inject MatchJoinAuthorization matchJoinAuthorization;

    @EventHandler
    public void onMatchAssignation(MatchAssignationEvent event) {
        // TODO: Bring user to this server
        matchJoinAuthorization.generateMatchAuthorization(event.getAssignation().getMatch(), event.getAssignation().getUser());
    }

}

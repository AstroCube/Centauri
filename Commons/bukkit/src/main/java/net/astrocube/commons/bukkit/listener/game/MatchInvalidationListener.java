package net.astrocube.commons.bukkit.listener.game;

import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.event.MatchInvalidateEvent;
import net.astrocube.api.bukkit.game.match.MatchStateUpdater;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.find.FindService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

public class MatchInvalidationListener implements Listener {

    private @Inject MatchStateUpdater matchStateUpdater;
    private @Inject FindService<Match> matchFindService;
    private @Inject MessageHandler<Player> messageHandler;
    private @Inject Plugin plugin;

    @EventHandler
    public void onMatchInvalidation(MatchInvalidateEvent event) {

        matchFindService.find(event.getMatch()).callback(response -> {

            if (response.isSuccessful() && response.getResponse().isPresent()) {

                Match match = response.getResponse().get();
                Set<String> involved = new HashSet<>();

                match.getPending().forEach(pending -> {
                    involved.addAll(pending.getInvolved());
                    involved.add(pending.getResponsible());
                });

                involved.addAll(match.getSpectators());
                match.getTeams().forEach(team -> {
                    //TODO: Add teams
                });



            } else {
                plugin.getLogger().warning("Can not invalidate match. Giving up...");
            }

        });

    }

}

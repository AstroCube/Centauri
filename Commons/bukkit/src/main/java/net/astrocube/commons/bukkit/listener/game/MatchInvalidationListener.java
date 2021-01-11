package net.astrocube.commons.bukkit.listener.game;

import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.event.match.MatchInvalidateEvent;
import net.astrocube.api.bukkit.game.event.spectator.SpectatorAssignEvent;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.match.MatchStateUpdater;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.bukkit.translation.mode.AlertMode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public class MatchInvalidationListener implements Listener {

    private @Inject MatchStateUpdater matchStateUpdater;
    private @Inject FindService<Match> matchFindService;
    private @Inject MessageHandler<Player> messageHandler;
    private @Inject Plugin plugin;

    @EventHandler
    public void onMatchInvalidation(MatchInvalidateEvent event) {

        matchFindService.find(event.getMatch()).callback(response -> {

            try {

                if (!response.isSuccessful() || !response.getResponse().isPresent()) {
                    throw new GameControlException("Can not retrieve from backend the match");
                }

                Match match = response.getResponse().get();
                Set<String> involved = new HashSet<>();

                match.getPending().forEach(pending -> {
                    involved.addAll(pending.getInvolved());
                    involved.add(pending.getResponsible());
                });

                involved.addAll(match.getSpectators());
                match.getTeams().forEach(team -> team.getMembers().forEach(teamMember -> {
                    if (teamMember.isActive()) {
                        involved.add(teamMember.getUser());
                    }
                }));

                matchStateUpdater.updateMatch(match, MatchDoc.Status.INVALIDATED);

                Bukkit.getOnlinePlayers().stream().filter
                        (p -> involved.contains(p.getDatabaseIdentifier())).forEach(player -> {
                    if (event.isGraceTime()) {
                        messageHandler.send(player, AlertMode.ERROR, "game.admin.invalidate-forced");
                        Bukkit.getPluginManager().callEvent(new SpectatorAssignEvent(player, match.getId()));
                    } else {
                        Bukkit.getScheduler().runTask(plugin, () ->
                                player.kickPlayer(messageHandler.get(player, "game.admin.invalidate")));
                    }
                });

            } catch (Exception e) {
                plugin.getLogger().log(Level.SEVERE, "Can not invalidate match.", e);
            }

        });

    }

}

package net.astrocube.commons.bukkit.listener.game.spectator;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.event.game.GameUserJoinEvent;
import net.astrocube.api.bukkit.game.event.spectator.SpectatorAssignEvent;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.match.MatchService;
import net.astrocube.api.bukkit.game.match.UserMatchJoiner;
import net.astrocube.api.bukkit.game.match.control.MatchParticipantsProvider;
import net.astrocube.api.bukkit.game.spectator.GhostEffectControl;
import net.astrocube.api.bukkit.game.spectator.LobbyItemProvider;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.cloud.CloudTeleport;
import net.astrocube.api.core.service.find.FindService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class SpectatorAssignListener implements Listener {

    private @Inject FindService<Match> findService;
    private @Inject MessageHandler<Player> messageHandler;
    private @Inject MatchService matchService;
    private @Inject Plugin plugin;

    private @Inject LobbyItemProvider lobbyItemProvider;
    private @Inject GhostEffectControl ghostEffectControl;

    @EventHandler
    public void onSpectatorAssign(SpectatorAssignEvent event) {
        findService.find(event.getMatch()).callback(response -> {
            try {

                if (!response.isSuccessful() || !response.getResponse().isPresent()) {
                    throw new GameControlException("Can not retrieve from backend the match");
                }

                Player player = event.getPlayer();
                Match match = response.getResponse().get();

                matchService.assignSpectator(event.getPlayer().getDatabaseIdentifier(), event.getMatch(), true);

                if (MatchParticipantsProvider.getOnlinePlayers(match).contains(player)) {
                    matchService.disqualify(match.getId(), player.getDatabaseIdentifier());
                }

                Bukkit.getPluginManager().callEvent(
                        new GameUserJoinEvent(
                                event.getMatch(),
                                player,
                                UserMatchJoiner.Origin.SPECTATING
                        )
                );

                lobbyItemProvider.provide(event.getPlayer(), 8);
                ghostEffectControl.addPlayer(event.getMatch(), player);

                Bukkit.getOnlinePlayers().forEach(online -> {
                    if (!MatchParticipantsProvider.getSpectatingPlayers(match).contains(online)) {
                        online.hidePlayer(player);
                        if (!MatchParticipantsProvider.getOnlinePlayers(match).contains(online)) {
                            player.hidePlayer(online);
                        }
                    }
                });

            } catch (Exception e) {
                plugin.getLogger().log(Level.SEVERE, "Can not invalidate match.", e);
                kickPlayer(event.getPlayer());
            }
        });
    }

    private void kickPlayer(Player player) {
        player.kickPlayer(ChatColor.RED + messageHandler.get(player, "game.lobby-error"));
    }

}

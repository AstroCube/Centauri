package net.astrocube.commons.bukkit.game.match.lobby;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.countdown.CountdownScheduler;
import net.astrocube.api.bukkit.game.lobby.LobbySessionManager;
import net.astrocube.api.bukkit.game.lobby.LobbySessionModifier;
import net.astrocube.api.bukkit.game.spectator.LobbyItemProvider;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.bukkit.game.match.control.CoreMatchParticipantsProvider;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;

@Singleton
public class CoreLobbySessionManager implements LobbySessionManager {

    private @Inject FindService<GameMode> findService;
    private @Inject FindService<User> userFindService;
    private @Inject CountdownScheduler countdownScheduler;
    private @Inject LobbySessionModifier lobbySessionModifier;
    private @Inject LobbyItemProvider lobbyItemProvider;
    private @Inject MessageHandler messageHandler;
    private @Inject Plugin plugin;

    @Override
    public void connectUser(Player player, Match match) {

        findService.find(match.getGameMode()).callback(gameModeResponse -> {

            Optional<SubGameMode> subMode = LobbySessionInvalidatorHelper.retrieveSubMode(gameModeResponse, match);

            if (!subMode.isPresent()) {
                Bukkit.getLogger().warning("There was an error while updating the match assignation.");
                player.kickPlayer(ChatColor.RED + messageHandler.get(player, "game.lobby-error"));
                return;
            }

            User user;
            try {
                user = userFindService.findSync(player.getDatabaseIdentifier());

                Set<String> waitingIds = CoreMatchParticipantsProvider.getPendingIds(match);

                Bukkit.getScheduler().runTask(plugin, () ->
                        lobbySessionModifier.ensureJoin(user, player, match, subMode.get()));


                if (waitingIds.size() >= subMode.get().getMinPlayers()) {
                    countdownScheduler.scheduleMatchCountdown(match);
                }

                player.getInventory().clear();

                lobbyItemProvider.provideBackButton(player, 8);

                if (player.hasPermission("commons.game.admin")) {
                    lobbyItemProvider.provideAdminButton(player, 7);
                }


            } catch (Exception e) {
                Bukkit.getLogger().log(Level.WARNING, "There was an error while updating the match assignation.", e);
                player.kickPlayer(ChatColor.RED + messageHandler.get(player, "game.lobby-error"));
            }

        });

    }

    @Override
    public void disconnectUser(Player player, Match match) {

        findService.find(match.getGameMode()).callback(gameModeResponse -> {

            Optional<SubGameMode> subMode = LobbySessionInvalidatorHelper.retrieveSubMode(gameModeResponse, match);

            if (!subMode.isPresent()) {
                Bukkit.getLogger().warning("There was an error while updating the match assignation.");
                return;
            }

            User user;
            try {

                user = userFindService.findSync(player.getDatabaseIdentifier());

                Set<String> waitingIds = CoreMatchParticipantsProvider.getPendingIds(match);
                Bukkit.getScheduler().runTask(plugin, () ->
                        lobbySessionModifier.ensureDisconnect(user, player, match, subMode.get()));

                if (waitingIds.size() >= subMode.get().getMinPlayers()) {
                    countdownScheduler.cancelMatchCountdown(match);
                }


            } catch (Exception e) {
                Bukkit.getLogger().warning("There was an error while updating the match assignation.");
            }

        });

    }



}

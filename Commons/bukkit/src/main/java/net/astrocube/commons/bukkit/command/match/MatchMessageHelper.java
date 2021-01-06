package net.astrocube.commons.bukkit.command.match;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.match.ActualMatchProvider;
import net.astrocube.api.bukkit.game.match.control.MatchParticipantsProvider;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.api.bukkit.translation.mode.AlertMode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;

public class MatchMessageHelper {

    private @Inject MatchParticipantsProvider matchParticipantsProvider;
    private @Inject FindService<User> findService;
    private @Inject DisplayMatcher displayMatcher;
    private @Inject MessageHandler<Player> messageHandler;
    private @Inject ActualMatchProvider actualMatchProvider;
    private @Inject Plugin plugin;

    public Optional<Match> checkInvolvedMatch(String playerId) {
        try {
            return actualMatchProvider.provide(playerId);
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "There was an error obtaining optional match");
            return Optional.empty();
        }
    }

    public boolean getCountAvailability(Optional<Match> providedMatch, Player player) {

        if (!providedMatch.isPresent()) {
            messageHandler.send(player, AlertMode.ERROR, "game.admin.not-active");
            return true;
        }

        if (providedMatch.get().getStatus() != MatchDoc.Status.LOBBY) {
            messageHandler.send(player, AlertMode.ERROR, "game.admin.started");
            return true;
        }

        return false;
    }

    public void alertInvolved(Set<User> involved, Match match, Player player, String message) {
        involved.addAll(matchParticipantsProvider.getMatchSpectators(match));

        findService.find(player.getDatabaseIdentifier()).callback(involvedUser -> {

            ChatColor color = ChatColor.YELLOW;
            if (involvedUser.isSuccessful() && involvedUser.getResponse().isPresent()) {
                color = ChatColor.valueOf(displayMatcher
                        .getRealmDisplay(involvedUser.getResponse().get()).getColor().toUpperCase());
            }

            ChatColor finalColor = color;
            involved.forEach(user -> {
                Player involvedPlayer = Bukkit.getPlayer(user.getUsername());
                involvedPlayer.sendMessage(
                        messageHandler.get(involvedPlayer, message)
                                .replace("%%player%%", finalColor + player.getName())
                );
            });
        });

    }

}

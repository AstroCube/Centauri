package net.astrocube.commons.bukkit.game.match.countdown;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.countdown.CountdownAlerter;
import net.astrocube.api.bukkit.game.match.control.MatchParticipantsProvider;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@Singleton
public class CoreCountdownAlerter implements CountdownAlerter {

    private final MatchParticipantsProvider matchParticipantsProvider;
    private final MessageHandler messageHandler;

    @Inject
    public CoreCountdownAlerter(
            MatchParticipantsProvider matchParticipantsProvider,
            MessageHandler messageHandler
    ) {
        this.matchParticipantsProvider = matchParticipantsProvider;
        this.messageHandler = messageHandler;
    }

    @Override
    public void alertCountdownSecond(Match match, int second) {
        this.matchParticipantsProvider.getMatchPending(match).forEach(user -> {
            Player player = Bukkit.getPlayer(user.getUsername());
            if (second > 5) {
                player.sendMessage(generateAlertMessage(player, second, ChatColor.AQUA));
            } else {
                player.sendMessage(generateAlertMessage(player, second, ChatColor.RED));
            }
        });
    }

    private String generateAlertMessage(Player player, int second, ChatColor color) {
        return messageHandler.get(player, "game.countdown")
                .replace("%%color%%", color + "")
                .replace("%%seconds%%", second + "");
    }


}

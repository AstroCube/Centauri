package net.astrocube.commons.bukkit.command.match;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.countdown.CountdownScheduler;
import net.astrocube.api.bukkit.game.match.control.MatchParticipantsProvider;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.Set;

public class MatchCancelCommand implements CommandClass {

    private @Inject MessageHandler<Player> messageHandler;
    private @Inject CountdownScheduler countdownScheduler;
    private @Inject MatchParticipantsProvider matchParticipantsProvider;
    private @Inject MatchMessageHelper matchMessageHelper;


    @Command(names = {"cancel"}, permission = "commons.match.cancel")
    public boolean onCommand(@Sender Player player) {

        Optional<Match> matchOptional = matchMessageHelper.checkInvolvedMatch(player.getDatabaseIdentifier());

        if (!matchOptional.isPresent()) {
            messageHandler.send(player, "game.admin.not-active");
            return true;
        }

        Set<User> involved = matchParticipantsProvider.getMatchPending(matchOptional.get());
        countdownScheduler.cancelMatchCountdown(matchOptional.get());
        matchMessageHelper.alertInvolved(involved, matchOptional.get(), player, "game.admin.stop");

        return true;
    }

}

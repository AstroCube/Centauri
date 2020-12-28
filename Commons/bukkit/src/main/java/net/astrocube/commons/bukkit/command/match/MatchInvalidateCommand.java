package net.astrocube.commons.bukkit.command.match;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.countdown.CountdownScheduler;
import net.astrocube.api.bukkit.game.event.match.MatchInvalidateEvent;
import net.astrocube.api.bukkit.game.match.control.MatchParticipantsProvider;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.Set;

public class MatchInvalidateCommand implements CommandClass {

    private @Inject MessageHandler<Player> messageHandler;
    private @Inject MatchMessageHelper matchMessageHelper;

    @Command(names = {"invalidate"}, permission = "commons.match.invalidate")
    public boolean onCommand(@Sender Player player) {

        Optional<Match> matchOptional = matchMessageHelper.checkInvolvedMatch(player.getDatabaseIdentifier());

        if (!matchOptional.isPresent()) {
            player.sendMessage(messageHandler.get(player, "game.admin.not-active"));
            messageHandler.send(player, "game.admin.not-active");
            return true;
        }

        if (matchOptional.get().getStatus() != MatchDoc.Status.RUNNING) {
            player.sendMessage(messageHandler.get(player, "game.admin.invalidate-not-stared"));
            return true;
        }

        Bukkit.getPluginManager().callEvent(new MatchInvalidateEvent(matchOptional.get().getId(),true));

        return true;
    }

}

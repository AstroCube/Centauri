package net.astrocube.commons.bukkit.command.match;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.event.match.MatchInvalidateEvent;
import net.astrocube.api.bukkit.translation.mode.AlertMode;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;

public class MatchInvalidateCommand implements CommandClass {

    private @Inject MessageHandler<Player> messageHandler;
    private @Inject MatchMessageHelper matchMessageHelper;

    @Command(names = {"invalidate"}, permission = "commons.match.invalidate")
    public boolean onCommand(@Sender Player player) {

        Optional<Match> matchOptional = matchMessageHelper.checkInvolvedMatch(player.getDatabaseIdentifier());

        if (!matchOptional.isPresent()) {
            messageHandler.send(player, AlertMode.ERROR, "game.admin.not-active");
            return true;
        }

        if (matchOptional.get().getStatus() != MatchDoc.Status.RUNNING) {
            messageHandler.send(player, AlertMode.ERROR, "game.admin.invalidate-not-stared");
            return true;
        }

        Bukkit.getPluginManager().callEvent(new MatchInvalidateEvent(matchOptional.get().getId(),true));

        return true;
    }

}

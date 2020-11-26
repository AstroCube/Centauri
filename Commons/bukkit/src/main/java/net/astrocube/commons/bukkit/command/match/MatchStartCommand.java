package net.astrocube.commons.bukkit.command.match;

import com.google.inject.Inject;
import me.fixeddev.ebcm.bukkit.parameter.provider.annotation.Sender;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.countdown.CountdownScheduler;
import net.astrocube.api.bukkit.game.match.ActualMatchProvider;
import net.astrocube.api.bukkit.game.match.control.MatchParticipantsProvider;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;

public class MatchStartCommand implements CommandClass {

    private @Inject ActualMatchProvider actualMatchProvider;
    private @Inject MessageHandler<Player> messageHandler;
    private @Inject CountdownScheduler countdownScheduler;
    private @Inject MatchParticipantsProvider matchParticipantsProvider;
    private @Inject Plugin plugin;


    @ACommand(names = {"start"})
    public boolean onCommand(@Injected(true) @Sender Player player, String seconds) {

        Optional<Match> matchOptional;

        try {
            matchOptional = actualMatchProvider.provide(player.getDatabaseIdentifier());
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "There was an error obtaining optional match");
            return true;
        }

        if (!matchOptional.isPresent()) {
            messageHandler.send(player, "game.admin.not-active");
            return true;
        }

        int secondsFixed;
        try {
            secondsFixed = Integer.parseInt(seconds);
        } catch (Exception e) {
            messageHandler.send(player, "game.admin.error");
            return true;
        }

        countdownScheduler.scheduleMatchCountdown(matchOptional.get(), secondsFixed, true);

        Set<User> involved = matchParticipantsProvider.getMatchPending(matchOptional.get());
        involved.addAll(matchParticipantsProvider.getMatchSpectators(matchOptional.get()));

        involved.forEach(user -> {
            Player involvedPlayer = Bukkit.getPlayer(user.getUsername());
            messageHandler.send(involvedPlayer, "game.admin.force");
        });

        return true;
    }

}

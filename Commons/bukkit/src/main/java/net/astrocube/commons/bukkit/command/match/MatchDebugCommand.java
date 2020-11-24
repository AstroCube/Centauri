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
import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRegistryHandler;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingSandboxProvider;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;

@ACommand(names = "debug")
public class MatchDebugCommand implements CommandClass {

    private @Inject MatchmakingSandboxProvider matchmakingSandboxProvider;
    private @Inject MessageHandler<Player> messageHandler;
    private @Inject Plugin plugin;

    @ACommand(names = {""})
    public boolean onCommand(@Injected(true) @Sender Player player) {
        try {
            matchmakingSandboxProvider.pairMatch(player);
        } catch (Exception e) {
            messageHandler.send(player, "game.admin.error");
            plugin.getLogger().log(Level.SEVERE, "There was an error pairing to matchmaking");
        }
        return true;
    }

}

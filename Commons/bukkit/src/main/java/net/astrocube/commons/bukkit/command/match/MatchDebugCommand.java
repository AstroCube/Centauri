package net.astrocube.commons.bukkit.command.match;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.match.ActualMatchProvider;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingSandboxProvider;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.logging.Level;


public class MatchDebugCommand implements CommandClass {

    private @Inject MatchmakingSandboxProvider matchmakingSandboxProvider;
    private @Inject ActualMatchProvider actualMatchProvider;
    private @Inject MessageHandler<Player> messageHandler;
    private @Inject Plugin plugin;

    @Command(names = "sandbox", permission = "commons.match.sandbox")
    public boolean execute(@Sender Player player) {

        try {

            Optional<Match> match = actualMatchProvider.provide(player.getDatabaseIdentifier());

            if (match.isPresent()) {
                messageHandler.send(player, "game.matchmaking.already");
            }

            matchmakingSandboxProvider.pairMatch(player);

        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "There was an error pairing user with a sandbox match.", e);
            messageHandler.send(player, "game.matchmaking.error");
        }

        return true;
    }

}

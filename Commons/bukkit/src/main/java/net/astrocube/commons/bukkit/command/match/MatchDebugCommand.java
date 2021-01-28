package net.astrocube.commons.bukkit.command.match;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.match.ActualMatchProvider;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingGenerator;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.translation.mode.AlertMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.logging.Level;


public class MatchDebugCommand implements CommandClass {

    private @Inject @Named("sandbox") MatchmakingGenerator matchmakingGenerator;
    private @Inject ActualMatchProvider actualMatchProvider;
    private @Inject MessageHandler messageHandler;
    private @Inject Plugin plugin;

    @Command(names = "sandbox", permission = "commons.match.sandbox")
    public boolean execute(@Sender Player player) {

        try {

            Optional<Match> match = actualMatchProvider.provide(player.getDatabaseIdentifier());

            if (match.isPresent()) {
                messageHandler.send(player, AlertMode.ERROR,"game.matchmaking.already");
            }

            matchmakingGenerator.pairMatch(player);

        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "There was an error pairing user with a sandbox match.", e);
            messageHandler.send(player, AlertMode.ERROR, "game.matchmaking.error");
        }

        return true;
    }

}

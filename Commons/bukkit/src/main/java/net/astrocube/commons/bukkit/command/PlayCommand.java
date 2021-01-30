package net.astrocube.commons.bukkit.command;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.match.ActualMatchProvider;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingGenerator;
import net.astrocube.api.bukkit.translation.mode.AlertMode;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.logging.Level;

public class PlayCommand implements CommandClass {

    private @Inject QueryService<GameMode> queryService;
    private @Inject MessageHandler<Player> messageHandler;
    private @Inject ActualMatchProvider actualMatchProvider;
    private @Inject @Named("core") MatchmakingGenerator matchmakingGenerator;
    private @Inject Plugin plugin;

    @Command(names = {"play"})
    public boolean onCommand(@Sender Player player, String mode, String subMode) {

        queryService.getAll().callback(queryResponse -> {

            if (!queryResponse.isSuccessful() || !queryResponse.getResponse().isPresent()) {
                messageHandler.send(player, AlertMode.ERROR, "game.play.error");
                return;
            }

            Optional<GameMode> foundMode = queryResponse.getResponse().get()
                    .getFoundModels()
                    .stream()
                    .filter(m -> m.getName().equalsIgnoreCase(mode))
                    .findAny();

            if (!foundMode.isPresent() || foundMode.get().getSubTypes() == null) {
                messageHandler.send(player, AlertMode.MUTED, "game.play.unknown");
                return;
            }

            Optional<SubGameMode> foundSubMode = foundMode.get().getSubTypes()
                    .stream()
                    .filter(s -> s.getName().equalsIgnoreCase(subMode))
                    .findAny();

            if (!foundSubMode.isPresent() || foundMode.get().getSubTypes() == null) {
                messageHandler.send(player, AlertMode.MUTED, "game.play.unknown");
                return;
            }

            try {

                Optional<Match> match = actualMatchProvider.provide(player.getDatabaseIdentifier());

                if (match.isPresent()) {
                    messageHandler.send(player, AlertMode.ERROR,"game.matchmaking.already");
                }

                matchmakingGenerator.pairMatch(player, foundMode.get(), foundSubMode.get());

            } catch (Exception e) {
                messageHandler.send(player, AlertMode.MUTED, "game.play.error");
                plugin.getLogger().log(Level.SEVERE, "Error while obtaining actual match", e);
            }

        });

        return true;
    }

}

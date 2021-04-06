package net.astrocube.commons.bukkit.command;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.game.match.MatchAvailabilityChecker;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingGenerator;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.cloud.CloudStatusProvider;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import net.astrocube.api.core.virtual.server.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.logging.Level;

public class PlayCommand implements CommandClass {

    private @Inject QueryService<GameMode> queryService;
    private @Inject FindService<Server> findService;
    private @Inject ActualMatchCache actualMatchCache;
    private @Inject MessageHandler messageHandler;
    private @Inject MatchmakingGenerator matchmakingGenerator;
    private @Inject CloudStatusProvider cloudStatusProvider;
    private @Inject MatchAvailabilityChecker matchAvailabilityChecker;
    private @Inject Plugin plugin;

    @Command(names = {"play"})
    public boolean onCommand(@Sender Player player, String mode, String subMode) {

        queryService.getAll().callback(queryResponse -> {

            if (!queryResponse.isSuccessful() || !queryResponse.getResponse().isPresent()) {
                messageHandler.sendIn(player, AlertModes.ERROR, "game.play.error");
                return;
            }

            Optional<GameMode> foundMode = queryResponse.getResponse().get()
                    .getFoundModels()
                    .stream()
                    .filter(m -> m.getName().equalsIgnoreCase(mode))
                    .findAny();

            if (!foundMode.isPresent() || foundMode.get().getSubTypes() == null) {
                messageHandler.sendIn(player, AlertModes.MUTED, "game.play.unknown");
                return;
            }

            Optional<SubGameMode> foundSubMode = foundMode.get().getSubTypes()
                    .stream()
                    .filter(s -> s.getName().equalsIgnoreCase(subMode))
                    .findAny();

            if (!foundSubMode.isPresent() || foundMode.get().getSubTypes() == null) {
                messageHandler.sendIn(player, AlertModes.MUTED, "game.play.unknown");
                return;
            }


            try {

                matchAvailabilityChecker.clearLegitMatches(player.getDatabaseIdentifier());

                Optional<Match> match = actualMatchCache.get(player.getDatabaseIdentifier());

                match.ifPresent(value -> findService.find(value.getServer()).callback(serverResponse -> {

                    if (!serverResponse.isSuccessful()) {
                        messageHandler.sendIn(player, AlertModes.MUTED, "game.play.error");
                    }

                    serverResponse.ifSuccessful(response -> {

                        if (cloudStatusProvider.getPlayerServer(player.getName())
                                .equalsIgnoreCase(response.getSlug())) {
                            messageHandler.sendIn(player, AlertModes.ERROR, "game.matchmaking.already");
                            return;
                        }

                        try {



                            matchmakingGenerator.pairMatch(player, foundMode.get(), foundSubMode.get());
                        } catch (Exception e) {
                            messageHandler.sendIn(player, AlertModes.MUTED, "game.play.error");
                            plugin.getLogger().log(Level.SEVERE, "Error while obtaining actual match", e);
                        }

                    });

                }));

                matchmakingGenerator.pairMatch(player, foundMode.get(), foundSubMode.get());

            } catch (Exception e) {
                messageHandler.sendIn(player, AlertModes.MUTED, "game.play.error");
                plugin.getLogger().log(Level.SEVERE, "Error while obtaining actual match", e);
            }

        });

        return true;
    }

}

package net.astrocube.commons.bukkit.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.game.spectator.SpectateRequestAssigner;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.logging.Level;

public class GoToCommand implements CommandClass {

    private @Inject QueryService<User> queryService;
    private @Inject ActualMatchCache actualMatchCache;
    private @Inject MessageHandler messageHandler;
    private @Inject SpectateRequestAssigner spectateRequestAssigner;
    private @Inject ObjectMapper objectMapper;
    private @Inject Plugin plugin;

    @Command(names = {"goto"}, permission = "commons.staff.goto")
    public boolean onCommand(@Sender Player player, String target) {


        ObjectNode nodes = objectMapper.createObjectNode();
        nodes.put("username", target);

        queryService.query(nodes).callback(usersCallback -> {

            if (!usersCallback.isSuccessful()) {
                messageHandler.sendIn(player, AlertModes.ERROR, "goto.error");
            }

            usersCallback.ifSuccessful(users -> {

                Optional<User> foundUser = users.getFoundModels().stream().findAny();

                if (!foundUser.isPresent()) {
                    messageHandler.sendIn(player, AlertModes.ERROR, "commands.unknown-player");
                }

                foundUser.ifPresent(user -> {

                    if (!user.getSession().isOnline()) {
                        messageHandler.sendIn(player, AlertModes.ERROR, "commands.unknown-player");
                        return;
                    }

                    try {

                        Optional<Match> matchOptional = actualMatchCache.get(user.getId());

                        if (matchOptional.isPresent()) {
                            spectateRequestAssigner.assignRequestToPlayer(
                                    matchOptional.get().getId(),
                                    player.getDatabaseIdentifier(),
                                    user.getId()
                            );
                            return;
                        }

                    } catch (Exception e) {
                        plugin.getLogger().log(Level.SEVERE, "Error while obtaining match cache", e);
                        messageHandler.sendIn(player, AlertModes.ERROR, "goto.error");
                        return;
                    }

                    System.out.println("Hi");
                    //TODO: Teleport to server without Control integration

                });

            });

        });


        return true;
    }

}

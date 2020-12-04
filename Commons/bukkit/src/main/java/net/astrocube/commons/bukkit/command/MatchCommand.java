package net.astrocube.commons.bukkit.command;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.SubCommandClasses;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingSandboxProvider;
import net.astrocube.commons.bukkit.command.match.MatchDebugCommand;
import net.astrocube.commons.bukkit.command.match.MatchStartCommand;
import org.bukkit.entity.Player;

@Command(names = {"match"})
@SubCommandClasses({
        MatchStartCommand.class,
        MatchDebugCommand.class
})
public class MatchCommand implements CommandClass {

    private @Inject
    MatchmakingSandboxProvider matchmakingSandboxProvider;

    @Command(names = {"", "help"})
    public boolean onCommand(@Sender Player player) {

        try {
            matchmakingSandboxProvider.pairMatch(player);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return true;
    }

}

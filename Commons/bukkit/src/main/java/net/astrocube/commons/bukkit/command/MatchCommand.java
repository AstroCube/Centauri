package net.astrocube.commons.bukkit.command;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.SubCommandClasses;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.commons.bukkit.command.match.MatchCancelCommand;
import net.astrocube.commons.bukkit.command.match.MatchDebugCommand;
import net.astrocube.commons.bukkit.command.match.MatchInvalidateCommand;
import net.astrocube.commons.bukkit.command.match.MatchStartCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

@Command(names = {"match"})
@SubCommandClasses({
        MatchStartCommand.class,
        MatchDebugCommand.class,
        MatchInvalidateCommand.class,
        MatchCancelCommand.class
})
public class MatchCommand implements CommandClass {

    private @Inject MessageHandler<Player> messageHandler;
    private @Inject Plugin plugin;

    @Command(names = {"", "help"})
    public boolean onCommand(@Sender Player player) {

        List<String> content = messageHandler.getMany(player, "game.admin.match-help").getContents();

        for (int i = 0; i < content.size(); i++) {

            /*
             * Im not going to change this. Fuck that and update de list name,
             * otherwise I will not be responsible for brain fucks while adding
             * new commands.
             *
             * - Tomato
             */
            if (i != 4 || plugin.getConfig().getBoolean("server.sandbox")) {
                player.sendMessage(content.get(i));
            }

        }

        return true;
    }

}

package net.astrocube.commons.bukkit.command.match;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingSandboxProvider;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


public class MatchDebugCommand implements CommandClass {

    private @Inject MatchmakingSandboxProvider matchmakingSandboxProvider;

    @Command(names = "sandbox")
    public boolean execute(@Sender Player player) {

        try {
            matchmakingSandboxProvider.pairMatch(player);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }



}

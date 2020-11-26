package net.astrocube.commons.bukkit.command.match;

import com.google.inject.Inject;
import me.fixeddev.ebcm.bukkit.parameter.provider.annotation.Sender;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingSandboxProvider;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


public class MatchDebugCommand implements CommandClass {

    private @Inject MatchmakingSandboxProvider matchmakingSandboxProvider;
    private @Inject MessageHandler<Player> messageHandler;
    private @Inject Plugin plugin;

    @ACommand(names = "debug")
    public boolean execute(@Injected(true) @Sender Player player) {

        try {
            matchmakingSandboxProvider.pairMatch(player);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }



}

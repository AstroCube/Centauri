package net.astrocube.commons.bukkit.command;

import com.google.inject.Inject;
import me.fixeddev.ebcm.bukkit.parameter.provider.annotation.Sender;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import me.fixeddev.ebcm.parametric.annotation.SubCommandClasses;
import me.yushust.message.MessageHandler;
import net.astrocube.commons.bukkit.command.match.MatchStartCommand;
import org.bukkit.entity.Player;

@ACommand(names = {"match"})
@SubCommandClasses({
        MatchStartCommand.class
})
public class MatchCommand implements CommandClass {

    private @Inject MessageHandler<Player> messageHandler;

    @ACommand(names = {"", "help"})
    public boolean onCommand(@Injected(true) @Sender Player player) {
        for (String message : messageHandler.getMany(player, "friend-help").getContents()) {
            player.sendMessage(message);
        }
        return true;
    }

}

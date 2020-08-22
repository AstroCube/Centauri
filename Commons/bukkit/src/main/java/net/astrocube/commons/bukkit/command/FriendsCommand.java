package net.astrocube.commons.bukkit.command;

import com.google.inject.Inject;
import me.fixeddev.ebcm.bukkit.parameter.provider.annotation.Sender;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import me.fixeddev.ebcm.parametric.annotation.SubCommandClasses;
import me.yushust.message.core.MessageProvider;
import net.astrocube.commons.bukkit.command.friends.AddSubCommand;
import net.astrocube.commons.bukkit.command.friends.ForceSubCommand;
import net.astrocube.commons.bukkit.command.friends.ListSubCommand;
import net.astrocube.commons.bukkit.command.friends.RemoveSubCommand;
import org.bukkit.entity.Player;

@ACommand(names = {"friends", "friend", "f"})
@SubCommandClasses({
        ListSubCommand.class,
        AddSubCommand.class,
        ForceSubCommand.class,
        RemoveSubCommand.class
})
public class FriendsCommand implements CommandClass {

    private @Inject MessageProvider<Player> messageProvider;

    @ACommand(names = {"", "help"})
    public boolean onCommand(@Injected(true) @Sender Player player) {
        for (String message : messageProvider.getMessages(player, "friend-help").getContents()) {
            player.sendMessage(message);
        }
        return true;
    }

}

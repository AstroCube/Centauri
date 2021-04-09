package net.astrocube.commons.bukkit.command;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.SubCommandClasses;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.commons.bukkit.command.friends.*;
import org.bukkit.entity.Player;

@Command(names = {"friends", "friend", "f"})
@SubCommandClasses({
        ListSubCommand.class,
        AddSubCommand.class,
        ForceSubCommand.class,
        RemoveSubCommand.class,
        DenySubCommand.class,
        AcceptSubCommand.class
})
public class FriendsCommand implements CommandClass {

    private @Inject MessageHandler messageHandler;

    @Command(names = {""})
    public boolean onCommand(@Sender Player player) {
        messageHandler.send(player, "friend.help");
        return true;
    }

}

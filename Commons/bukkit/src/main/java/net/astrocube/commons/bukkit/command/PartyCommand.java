package net.astrocube.commons.bukkit.command;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.SubCommandClasses;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.commons.bukkit.command.party.PartyInviteCommand;
import org.bukkit.entity.Player;

import javax.inject.Inject;

@Command(names = {"party", "p"})
@SubCommandClasses({
        PartyInviteCommand.class
})
public class PartyCommand implements CommandClass {

    private @Inject MessageHandler<Player> messageHandler;

    @Command(names = "")
    public void onCommand(@Sender Player player) {
        messageHandler.send(player, "party-help");
    }

}

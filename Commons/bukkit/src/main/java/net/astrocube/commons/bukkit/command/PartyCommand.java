package net.astrocube.commons.bukkit.command;

import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import org.bukkit.entity.Player;

import javax.inject.Inject;

@Command(names = {"party", "p"})
public class PartyCommand {

    private @Inject MessageHandler<Player> messageHandler;

    @Command(names = "")
    public void onCommand(@Sender Player player) {
        messageHandler.send(player, "party-help");
    }

}

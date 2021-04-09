package net.astrocube.commons.bukkit.command;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import org.bukkit.entity.Player;

import javax.inject.Inject;

@Command(names = "hub")
public class LobbyCommand implements CommandClass {

    private @Inject MessageHandler messageHandler;

    @Command(names = {"hub", "lobby", "l"})
    public void onCommandPerform(@Sender Player player) {



    }

}

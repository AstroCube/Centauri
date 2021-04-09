package net.astrocube.commons.bukkit.command;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import org.bukkit.entity.Player;

import javax.inject.Inject;

@Command(names = "fly", permission="commons.fly")
public class FlyCommand implements CommandClass {

    private @Inject MessageHandler messageHandler;

    @Command(names = "")
    public void fly(@Sender Player player, @OptArg Player target) {
        if (target == null) {
            target = player;
        } else if (target != player && player.hasPermission("commons.fly.others")) {
            messageHandler.send(player, "commands.translation.command.no-permission");
            return;
        }

        boolean newState = !target.getAllowFlight();
        String messagePath = "fly-" + (newState ? "true" : "false");
        target.setAllowFlight(newState);
        messageHandler.send(target, messagePath);
        if (player != target) {
            messageHandler.sendReplacing(
                    player, messagePath + "-other",
                    "%target%", target.getName()
            );
        }
    }

}

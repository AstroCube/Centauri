package net.astrocube.commons.bukkit.command;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.SubCommandClasses;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.commons.bukkit.command.party.*;
import org.bukkit.entity.Player;

import javax.inject.Inject;

@Command(names = {"party", "p"})
@SubCommandClasses({
	PartyInviteCommand.class,
	PartyAcceptCommand.class,
	PartyLeaveCommand.class,
	PartyDisbandCommand.class,
	PartyRejectCommand.class,
	PartyKickOfflineCommand.class,
	PartyPromoteCommand.class
})
public class PartyCommand implements CommandClass {

	private @Inject MessageHandler messageHandler;

	@Command(names = "")
	public void onCommand(@Sender Player player) {
		messageHandler.send(player, "party-help");
	}

}

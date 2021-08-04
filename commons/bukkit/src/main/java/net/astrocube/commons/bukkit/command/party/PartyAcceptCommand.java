package net.astrocube.commons.bukkit.command.party;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.party.PartyService;
import net.astrocube.api.core.service.update.UpdateService;
import net.astrocube.api.core.virtual.party.Party;
import net.astrocube.api.core.virtual.party.PartyDoc;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.Optional;

@Command(names = "accept")
public class PartyAcceptCommand implements CommandClass {

	private @Inject PartyService partyService;

	@Command(names = "")
	public void execute(
		@Sender Player invited,
		String targetName) {
		partyService.handleAcceptInvitation(invited, targetName);
	}

}

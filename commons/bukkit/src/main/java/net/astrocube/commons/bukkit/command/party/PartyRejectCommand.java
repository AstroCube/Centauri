package net.astrocube.commons.bukkit.command.party;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.party.PartyService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.Optional;
import java.util.Set;

@Command(names = "reject")
public class PartyRejectCommand implements CommandClass {

	private @Inject PartyService partyService;
	private @Inject MessageHandler messageHandler;

	@Command(names = "")
	public void reject(
		@Sender Player player
	) {
		Set<String> invitations = partyService.getInvitations(player.getName());
		/*
		if (!optInviter.isPresent()) {
			messageHandler.send(player, "no-party-invitation");
			return;
		}

		Player inviter = Bukkit.getPlayerByDatabaseId(optInviter.get());
		if (inviter != null) {
			messageHandler.sendReplacing(
				inviter, "rejected-party-invitation",
				"%player%", player.getName()
			);
		}

		messageHandler.send(player, "rejected-invitation");
		partyService.removeInvite(player.getName());
		 */
	}

}


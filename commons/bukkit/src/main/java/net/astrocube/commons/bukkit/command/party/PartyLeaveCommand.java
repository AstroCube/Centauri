package net.astrocube.commons.bukkit.command.party;

import com.google.inject.Inject;
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

import java.util.Optional;

@Command(names = "leave")
public class PartyLeaveCommand implements CommandClass {

	private @Inject PartyService partyService;
	private @Inject MessageHandler messageHandler;
	private @Inject UpdateService<Party, PartyDoc.Partial> partyUpdateService;

	@Command(names = "")
	public void leave(
		@Sender Player player
	) {
		Optional<Party> optParty = partyService.getPartyOf(player.getDatabaseIdentifier());
		if (!optParty.isPresent()) {
			messageHandler.send(player, "cannot-leave.not-in-party");
		} else {
			Party party = optParty.get();
			if (party.getLeader().equals(player.getDatabaseIdentifier())) {
				messageHandler.send(player, "cannot-leave.leader");
				return;
			}
			party.getMembers().remove(player.getDatabaseIdentifier());
			partyUpdateService.update(party);
			messageHandler.send(player, "left-party");
			for (String memberId : party.getMembers()) {
				Player member = Bukkit.getPlayerByIdentifier(memberId);
				if (member != null) {
					messageHandler.sendReplacing(
						member, "other-left-party",
						"%player%", player.getName()
					);
				}
			}
		}
	}

}

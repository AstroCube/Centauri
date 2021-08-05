package net.astrocube.commons.bukkit.command.party;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.party.PartyService;
import net.astrocube.api.core.virtual.party.Party;
import net.astrocube.commons.bukkit.party.util.PartyUtil;
import org.bukkit.entity.Player;

@Command(names = "list")
public class PartyListCommand implements CommandClass {

	@Inject
	private PartyService partyService;

	@Inject
	private PartyUtil partyUtil;

	@Inject
	private MessageHandler messageHandler;

	@Command(names = "")
	public void main(@Sender Player sender) {
		Party party = partyUtil.getPartyOrSendError(sender);

		if (party != null) {
			messageHandler.sendReplacing(sender, "party-list");
		}

	}

}

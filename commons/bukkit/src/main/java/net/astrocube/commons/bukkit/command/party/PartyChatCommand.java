package net.astrocube.commons.bukkit.command.party;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.Text;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.party.PartyMessenger;
import net.astrocube.api.bukkit.party.PartyService;
import net.astrocube.api.core.virtual.party.Party;
import org.bukkit.entity.Player;

import java.util.Optional;

@Command(names = "chat")
public class PartyChatCommand implements CommandClass {

	@Inject
	private PartyService partyService;

	@Inject
	private PartyMessenger partyMessenger;

	@Inject
	private MessageHandler messageHandler;

	@Command(names = "")
	public void main(@Sender Player sender, @Text String message) {
		Optional<Party> optional = partyService.getPartyOf(sender.getDatabaseIdentifier());

		if (!optional.isPresent()) {
			messageHandler.send(sender, "no-party-now");
			return;
		}

		partyMessenger.chat(sender, message, optional.get());
	}

}

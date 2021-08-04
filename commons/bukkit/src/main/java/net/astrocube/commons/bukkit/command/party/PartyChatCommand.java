package net.astrocube.commons.bukkit.command.party;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.Text;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
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
	private PartyMessenger partyChatHandler;

	@Command(names = "")
	public void main(@Sender Player player, @Text String message) {
		Optional<Party> optional = partyService.getPartyOf(player.getDatabaseIdentifier());

		if (!optional.isPresent()) {
			//send message "you no has a party"
			return;
		}

		partyChatHandler.chatParty(optional.get(), player, message);
	}

}

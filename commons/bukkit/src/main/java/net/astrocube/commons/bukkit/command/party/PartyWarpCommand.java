package net.astrocube.commons.bukkit.command.party;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.party.PartyService;
import net.astrocube.api.core.virtual.party.Party;
import org.bukkit.entity.Player;

import java.util.Optional;

@Command(names = "")
public class PartyWarpCommand implements CommandClass {

	@Inject
	private PartyService partyService;

	@Inject
	private MessageHandler messageHandler;

	@Command(names = "")
	public void main(@Sender Player sender) {
		Optional<Party> optional = partyService.getPartyOf(sender.getDatabaseId());

		if (!optional.isPresent()) {
			messageHandler.send(sender, "already-in-party");
			return;
		}

		partyService.warp(sender, optional.get());
	}

}

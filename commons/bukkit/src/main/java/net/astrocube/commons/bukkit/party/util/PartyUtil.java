package net.astrocube.commons.bukkit.party.util;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.party.PartyService;
import net.astrocube.api.core.virtual.party.Party;
import org.bukkit.entity.Player;

import java.util.Optional;

public class PartyUtil {

	@Inject
	private PartyService partyService;

	@Inject
	private MessageHandler messageHandler;

	public Party getPartyOrSendError(Player player) {
		Optional<Party> optional = partyService.getPartyOf(player.getDatabaseIdentifier());

		if (!optional.isPresent()) {
			messageHandler.send(player, "cannot-disband.not-in-party");
			return null;
		}

		return optional.get();
	}

}

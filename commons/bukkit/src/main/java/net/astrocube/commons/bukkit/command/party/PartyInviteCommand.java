package net.astrocube.commons.bukkit.command.party;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.party.PartyService;
import net.astrocube.api.core.virtual.party.Party;
import net.astrocube.commons.bukkit.utils.UserUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Command(names = {"invite", "add"})
public class PartyInviteCommand implements CommandClass {

	private @Inject PartyService partyService;
	private @Inject MessageHandler messageHandler;

	@Command(names = "")
	public void invite(
		@Sender Player player,
		String target
	) {

		Player playerTarget = Bukkit.getPlayer(target);

		if (playerTarget != null) {
			if (UserUtils.checkSamePlayer(player, playerTarget, messageHandler)) {
				return;
			}
		}

		String id = player.getDatabaseIdentifier();
		Party party = partyService.getPartyOf(id)
			.orElseGet(() -> {
				messageHandler.send(player, "created-party");
				return partyService.createParty(id);
			});

		partyService.handleInvitation(player, party, target);
	}

}

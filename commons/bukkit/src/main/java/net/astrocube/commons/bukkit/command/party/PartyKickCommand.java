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

@Command(names = "kick")
public class PartyKickCommand implements CommandClass {

	@Inject private PartyService partyService;
	@Inject private MessageHandler messageHandler;
	@Inject private UpdateService<Party, PartyDoc.Partial> partyUpdateService;

	@Command(names = "")
	public void execute(
		@Sender Player player,
		Player target
	) {
		String playerId = player.getDatabaseId();
		String targetId = target.getDatabaseId();

		Optional<Party> optParty = partyService.getPartyOf(targetId);
		Party party;

		if (!optParty.isPresent()) {
			messageHandler.send(player, "cannot-kick.not-in-party");
			return;
		} else if (!(party = optParty.get()).getLeader().equals(playerId)) {
			messageHandler.send(player, "cannot-kick.not-leader");
			return;
		} else if (!party.getMembers().contains(targetId)) {
			messageHandler.send(player, "cannot-kick.not-same-party");
			return;
		}

		for (String memberId : party.getMembers()) {
			Player member = Bukkit.getPlayer(memberId);
			if (member == null) {
				continue;
			}
			String path = "kicked";
			if (memberId.equals(playerId)) {
				path = "invoker-" + path;
			} else if (!memberId.equals(targetId)) {
				path = "other-" + path;
			}

			messageHandler.sendReplacing(
				member, path,
				"%player%", player.getName(),
				"%target%", target.getName()
			);
		}

		party.getMembers().remove(targetId);
		partyUpdateService.update(party);
	}

}

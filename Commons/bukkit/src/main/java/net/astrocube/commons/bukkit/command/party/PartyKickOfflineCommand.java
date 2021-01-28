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
import java.util.Iterator;
import java.util.Optional;

@Command(names = "kickoffline")
public class PartyKickOfflineCommand
        implements CommandClass {

    @Inject private PartyService partyService;
    @Inject private UpdateService<Party, PartyDoc.Partial> partyUpdateService;
    @Inject private MessageHandler<Player> messageHandler;

    @Command(names = "")
    public void execute(
            @Sender Player player
    ) {
        String playerId = player.getDatabaseIdentifier();
        Optional<Party> optParty = partyService.getPartyOf(playerId);
        Party party;

        if (!optParty.isPresent()) {
            messageHandler.send(player, "cannot-kickoffline.not-in-party");
            return;
        } else if (!(party = optParty.get()).getLeader().equals(playerId)) {
            return;
        }

        Iterator<String> memberIdsIterator = party.getMembers().iterator();
        while (memberIdsIterator.hasNext()) {
            String memberId = memberIdsIterator.next();
            Player member = Bukkit.getPlayerByIdentifier(memberId);
            if (member == null) {
                memberIdsIterator.remove();
            } else {
                String path = (memberId.equals(playerId) ? "" : "other-") + "kicked-offline";
                messageHandler.sendReplacing(
                        member, path,
                        "%%player%%", player.getName()
                );
            }
        }

        partyUpdateService.update(party);
    }

}

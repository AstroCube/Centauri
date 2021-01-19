package net.astrocube.commons.bukkit.command.party;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.party.PartyService;
import net.astrocube.api.core.service.delete.DeleteService;
import net.astrocube.api.core.virtual.party.Party;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.Optional;

@Command(names = "disband")
public class PartyDisbandCommand implements CommandClass {

    private @Inject PartyService partyService;
    private @Inject DeleteService<Party> partyDeleteService;
    private @Inject MessageHandler<Player> messageHandler;

    @Command(names = "")
    public void disband(
            @Sender Player player
    ) {
        Optional<Party> optParty = partyService.getPartyOf(player.getDatabaseIdentifier());
        if (!optParty.isPresent()) {
            messageHandler.send(player, "cannot-disband.not-in-party");
            return;
        }

        Party party = optParty.get();
        if (!player.getDatabaseIdentifier().equals(party.getLeader())) {
            messageHandler.send(player, "cannot-disband.not-leader");
            return;
        }

        for (String memberId : party.getMembers()) {
            Player member = Bukkit.getPlayerByIdentifier(memberId);
            if (member != null) {
                messageHandler.sendReplacing(
                        member, "party-disbanded",
                        "%%player%%", player.getName()
                );
            }
        }
        partyDeleteService.delete(party.getId());
    }

}

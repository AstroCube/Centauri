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

@Command(names = "accept")
public class PartyAcceptCommand implements CommandClass {

    private @Inject PartyService partyService;
    private @Inject UpdateService<Party, PartyDoc.Partial> updateService;
    private @Inject MessageHandler messageHandler;

    @Command(names = "")
    public void execute(
            @Sender Player player
    ) {
        if (partyService.getPartyOf(player.getDatabaseIdentifier()).isPresent()) {
            messageHandler.send(player, "already-in-party");
            return;
        }
        Optional<String> optInviter = partyService.getPartyInviter(player.getName());
        if (!optInviter.isPresent()) {
            messageHandler.send(player, "no-party-invitation");
        } else {
            String inviterId = optInviter.get();
            Player inviter = Bukkit.getPlayerByIdentifier(inviterId);
            if (inviter == null) {
                messageHandler.send(player, "party-offline-inviter");
                return;
            }
            Optional<Party> optParty = partyService.getPartyOf(inviterId);
            partyService.removeInvite(player.getName());
            if (!optParty.isPresent()) {
                messageHandler.sendReplacing(
                        player, "no-party-now",
                        "%inviter%", inviter.getName()
                );
            } else {
                Party party = optParty.get();
                for (String memberId : party.getMembers()) {
                    Player member = Bukkit.getPlayerByIdentifier(memberId);
                    if (member != null) {
                        messageHandler.send(
                                member, "other-joined-party",
                                "%player%", player.getName()
                        );
                    }
                }
                party.getMembers().add(player.getDatabaseIdentifier());
                messageHandler.send(
                        player, "joined-party",
                        "%inviter%", inviter.getName()
                );
                updateService.update(party);
            }
        }
    }

}

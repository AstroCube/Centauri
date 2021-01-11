package net.astrocube.commons.bukkit.command.party;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.party.PartyService;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.Optional;

@Command(names = "accept")
public class PartyAcceptCommand implements CommandClass {

    private @Inject PartyService partyService;
    private @Inject MessageHandler<Player> messageHandler;

    @Command(names = "")
    public void execute(
            @Sender Player player
    ) {
        Optional<String> inviter = partyService.getPartyInviter(player.getDatabaseIdentifier());
        if (!inviter.isPresent()) {
            messageHandler.send(player, "no-party-invitation");
        } else {
            String inviterName = inviter.get();
        }
    }

}

package net.astrocube.commons.bukkit.party;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.party.PartyChatHandler;
import net.astrocube.api.bukkit.party.PartyService;
import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.virtual.party.Party;
import net.astrocube.commons.bukkit.party.channel.message.PartyMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CorePartyChatHandler implements PartyChatHandler {

	@Inject
	private Channel<PartyMessage> partyMessageChannel;

	@Inject
	private PartyService partyService;

	@Inject
	private MessageHandler messageHandler;

	@Override
	public void chatParty(Party party, Player player, String message) {
		try {
			partyMessageChannel.sendMessage(new PartyMessage(player.getName(), message, party.getId()), null);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void chatServer(String partyId, String sender, String message) {
		partyService.getParty(partyId).ifPresent(party -> {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (party.getMembers().contains(player.getDatabaseIdentifier())) {
					messageHandler.sendReplacing(player, "party-chat", "%player%", sender, "%message%", message);
				}
			}
		});

	}
}

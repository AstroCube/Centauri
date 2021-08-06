package net.astrocube.commons.bukkit.party.channel.listener;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.party.PartyService;
import net.astrocube.api.core.message.MessageListener;
import net.astrocube.api.core.message.MessageMetadata;
import net.astrocube.commons.bukkit.party.channel.message.PartyMessage;
import org.bukkit.Bukkit;

public class PartyMessageListener implements MessageListener<PartyMessage> {

	@Inject
	private PartyService partyService;

	@Inject
	private MessageHandler messageHandler;

	@Override
	public void handleDelivery(PartyMessage message, MessageMetadata properties) {
		System.out.println("Received PartyMessageListener");
		partyService.getParty(message.getPartyId())
			.ifPresent(party -> Bukkit.getOnlinePlayers().forEach(player -> {
				if (party.getMembers().contains(player.getDatabaseIdentifier()) || party.getLeader().equals(player.getDatabaseIdentifier())) {
					if (message.getReplacements() != null) {
						messageHandler.sendReplacing(player, message.getPath(), (Object) message.getReplacements());
					} else {
						messageHandler.send(player, message.getPath());
					}
				}
			}));
	}

}

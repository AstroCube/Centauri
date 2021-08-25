package net.astrocube.commons.bukkit.party.channel.listener;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.party.PartyMessenger;
import net.astrocube.api.bukkit.party.PartyService;
import net.astrocube.api.core.message.MessageListener;
import net.astrocube.api.core.message.MessageMetadata;
import net.astrocube.api.core.virtual.party.Party;
import net.astrocube.commons.bukkit.party.channel.message.PartyMessage;

import java.util.Optional;

public class PartyMessageListener implements MessageListener<PartyMessage> {

	@Inject
	private PartyService partyService;

	@Inject
	private PartyMessenger partyMessenger;

	@Override
	public void handleDelivery(PartyMessage message, MessageMetadata properties) {
		partyService.getParty(message.getPartyId()).ifPresent(party -> partyMessenger.sendMessageServer(party, message.getPath(), message.getReplacements()));
	}

}

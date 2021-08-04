package net.astrocube.commons.bukkit.party.channel.listener;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.party.PartyChatHandler;
import net.astrocube.api.core.message.MessageListener;
import net.astrocube.api.core.message.MessageMetadata;
import net.astrocube.commons.bukkit.party.channel.message.PartyMessage;

public class PartyMessageListener implements MessageListener<PartyMessage> {

	@Inject
	private PartyChatHandler partyChatHandler;

	@Override
	public void handleDelivery(PartyMessage message, MessageMetadata properties) {
		partyChatHandler.chatServer(message.getSenderMessage(), message.getMessage());
	}

}

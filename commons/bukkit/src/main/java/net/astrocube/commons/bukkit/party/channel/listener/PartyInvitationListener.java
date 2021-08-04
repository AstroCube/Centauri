package net.astrocube.commons.bukkit.party.channel.listener;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.party.PartyService;
import net.astrocube.api.core.message.MessageListener;
import net.astrocube.api.core.message.MessageMetadata;
import net.astrocube.commons.bukkit.party.channel.message.PartyInvitationMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PartyInvitationListener implements MessageListener<PartyInvitationMessage> {

	@Inject
	private PartyService service;

	@Override
	public void handleDelivery(PartyInvitationMessage message, MessageMetadata properties) {
		Player player = Bukkit.getPlayer(message.getPlayNameInvited());

		if (player != null) {
			service.handleRequestInvitation(message.getPlayerNameInviter(), player);
		}

	}

}

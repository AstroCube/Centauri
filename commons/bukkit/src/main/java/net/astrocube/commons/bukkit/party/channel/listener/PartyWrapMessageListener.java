package net.astrocube.commons.bukkit.party.channel.listener;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.party.PartyService;
import net.astrocube.api.bukkit.teleport.ServerTeleportRetry;
import net.astrocube.api.core.message.MessageListener;
import net.astrocube.api.core.message.MessageMetadata;
import net.astrocube.commons.bukkit.party.channel.message.PartyWarpMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PartyWrapMessageListener implements MessageListener<PartyWarpMessage> {

	@Inject
	private PartyService partyService;

	@Inject
	private ServerTeleportRetry serverTeleportRetry;

	@Override
	public void handleDelivery(PartyWarpMessage message, MessageMetadata properties) {
		partyService.getParty(message.getPartyId())
			.ifPresent(party -> {
				for (Player player : Bukkit.getOnlinePlayers()) {
					serverTeleportRetry.attemptTeleport(player.getName(), message.getServerName(), 1, 3);
				}
			});
	}

}

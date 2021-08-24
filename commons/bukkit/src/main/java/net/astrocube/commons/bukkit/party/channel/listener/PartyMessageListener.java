package net.astrocube.commons.bukkit.party.channel.listener;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.party.PartyService;
import net.astrocube.api.core.message.MessageListener;
import net.astrocube.api.core.message.MessageMetadata;
import net.astrocube.api.core.virtual.party.Party;
import net.astrocube.commons.bukkit.party.channel.message.PartyMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;

public class PartyMessageListener implements MessageListener<PartyMessage> {

	@Inject
	private PartyService partyService;

	@Inject
	private MessageHandler messageHandler;

	@Override
	public void handleDelivery(PartyMessage message, MessageMetadata properties) {
		Optional<Party> optional = partyService.getParty(message.getPartyId());

		if (optional == null) {
			System.out.println("Optional es null Party Id  " + message.getPartyId());
			return;
		}

		if (optional.isPresent()) {
			System.out.println("Si existe la party");
			Party party = optional.get();

			for (Player player : Bukkit.getOnlinePlayers()) {
				if (party.getMembers().contains(player.getDatabaseId()) || party.getLeader().equals(player.getDatabaseId())) {
					System.out.println("Si es un usuario de la party");

					if (message.getReplacements() != null) {
						messageHandler.sendReplacing(player, message.getPath(), (Object) message.getReplacements());
					} else {
						messageHandler.send(player, message.getPath());
					}

					System.out.println("Mensaje enviado");

				}
			}

		} else {
			System.out.println("No existe la party");
		}

	}

	/*
			partyService.getParty(message.getPartyId())
			.ifPresent(party -> Bukkit.getOnlinePlayers().forEach(player -> {
				if (party.getMembers().contains(player.getDatabaseId()) || party.getLeader().equals(player.getDatabaseId())) {
					if (message.getReplacements() != null) {
						messageHandler.sendReplacing(player, message.getPath(), (Object) message.getReplacements());
					} else {
						messageHandler.send(player, message.getPath());
					}
				}
			}));
	 */

}

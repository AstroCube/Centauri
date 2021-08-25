package net.astrocube.commons.bukkit.party;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.party.PartyMessenger;
import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.message.Messenger;
import net.astrocube.api.core.virtual.party.Party;
import net.astrocube.commons.bukkit.party.channel.message.PartyMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CorePartyMessenger implements PartyMessenger {

	@Inject
	private Messenger messenger;

	@Inject
	private MessageHandler messageHandler;

	@Override
	public void sendMessage(Party party, String path, String... replacements) {

		Channel<PartyMessage> partyMessageChannel = messenger.getChannel(PartyMessage.class);

		try {
			partyMessageChannel.sendMessage(new PartyMessage(path, party.getId(), replacements), null);
			sendMessageServer(party, path, replacements);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendMessageServer(Party party, String path, String... replacements) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (party.getMembers().contains(player.getDatabaseIdentifier()) || party.getLeader().equals(player.getDatabaseIdentifier())) {
				System.out.println("Si es un usuario de la party");

				if (replacements != null) {
					messageHandler.sendReplacing(player, path, (Object) replacements);
				} else {
					messageHandler.send(player, path);
				}

				System.out.println("Mensaje enviado");

			}
		}
	}
}
package net.astrocube.commons.bukkit.party;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import net.astrocube.api.bukkit.party.PartyMessenger;
import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.message.Messenger;
import net.astrocube.api.core.virtual.party.Party;
import net.astrocube.commons.bukkit.party.channel.message.PartyMessage;

public class CorePartyMessenger implements PartyMessenger {

	@Inject
	private Messenger messenger;

	@Override
	public void sendMessage(Party party, String path, String... replacements) {

		Channel<PartyMessage> partyMessageChannel = messenger.getChannel(PartyMessage.class);

		try {
			partyMessageChannel.sendMessage(new PartyMessage(path, party.getId(), replacements), null);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
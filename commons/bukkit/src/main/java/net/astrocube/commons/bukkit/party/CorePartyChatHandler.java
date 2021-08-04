package net.astrocube.commons.bukkit.party;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.party.PartyChatHandler;
import net.astrocube.api.core.message.Channel;
import net.astrocube.commons.bukkit.party.channel.message.PartyMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CorePartyChatHandler implements PartyChatHandler {

	@Inject
	private Channel<PartyMessage> partyMessageChannel;

	@Inject
	private MessageHandler messageHandler;

	@Override
	public void chatGlobal(Player player, String message) {
		try {
			partyMessageChannel.sendMessage(new PartyMessage(player.getName(), message), null);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void chatServer(String playerName, String message) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			messageHandler.sendReplacing(player, "party-chat", "%player%", playerName, "%message%", message);
		}
	}
}

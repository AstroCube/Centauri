package net.astrocube.commons.bukkit.server.broadcast;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BroadcastSender {

	@Inject
	private MessageHandler messageHandler;

	public void sendToServer(String message) {
		Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(getPrefix(player) + message));
	}

	private String getPrefix(Player player) {
		return messageHandler.get(player , "prefix");
	}

}

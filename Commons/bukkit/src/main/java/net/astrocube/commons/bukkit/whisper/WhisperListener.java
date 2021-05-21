package net.astrocube.commons.bukkit.whisper;

import me.fixeddev.minecraft.player.Player;
import net.astrocube.api.core.message.MessageListener;
import net.astrocube.api.core.message.MessageMetadata;
import org.bukkit.Bukkit;

public class WhisperListener implements MessageListener<WhisperMessage> {

	private final me.yushust.message.MessageHandler messageHandler;

	WhisperListener(me.yushust.message.MessageHandler messageHandler) {
		this.messageHandler = messageHandler;
	}

	@Override
	public void handleDelivery(WhisperMessage message, MessageMetadata properties) {
		Player target = Bukkit.getPlayerByIdentifier(message.getTargetId());

		if (target == null) {
			return; // The player is not online there.
		}

		messageHandler
			.sendReplacing(target, "whisper.target",
				"%sender%", message.getSenderDisplay(),
				"%target%", message.getTargetDisplay(),
				"%message%", message.getMessage());
	}

}

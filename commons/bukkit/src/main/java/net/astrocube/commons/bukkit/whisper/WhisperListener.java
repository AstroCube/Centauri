package net.astrocube.commons.bukkit.whisper;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.core.message.MessageListener;
import net.astrocube.api.core.message.MessageMetadata;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class WhisperListener implements MessageListener<WhisperMessage> {

	@Inject private MessageHandler messageHandler;

	@Override
	public void handleDelivery(WhisperMessage message, MessageMetadata properties) {
		Player target = Bukkit.getPlayerByDatabaseId(message.getTargetId());

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

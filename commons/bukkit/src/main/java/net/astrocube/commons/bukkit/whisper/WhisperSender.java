package net.astrocube.commons.bukkit.whisper;

import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.logging.Level;

public class WhisperSender {

	private @Inject WhisperManager whisperManager;
	private @Inject MessageHandler messageHandler;

	public void sendWhisper(Player sender, User targetUser, User user, String message) {
		whisperManager.sendWhisper(sender, targetUser, user, message)
			.whenComplete((whisperResponse, error) -> {
				try {
					if (whisperResponse.getResult() == WhisperResponse.Result.FAILED_OFFLINE) {
						messageHandler.sendIn(sender, AlertModes.ERROR, "commands.player.offline");
					} else if (whisperResponse.getResult() == WhisperResponse.Result.FAILED_ERROR) {
						whisperResponse.getErrors().forEach(e ->
							Bukkit.getLogger().log(Level.WARNING, "Failed to send message", e));
					}
					// handle more errors!
				} catch (Exception e) {
					//TODO: remove this
					e.printStackTrace();
				}
			});
	}

}

package net.astrocube.commons.bukkit.command;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.Text;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.api.core.virtual.user.UserDoc;
import net.astrocube.commons.bukkit.whisper.WhisperSender;
import org.bukkit.entity.Player;

import javax.inject.Inject;

@Command(names = {"reply", "r"})
public class ReplyCommand implements CommandClass {

	private @Inject FindService<User> userFindService;
	private @Inject MessageHandler messageHandler;
	private @Inject WhisperSender whisperSender;

	@Command(names = "")
	public void execute(@Sender Player player, @Text String message) {
		userFindService.find(player.getDatabaseId())
			.callback(userResponse -> {

				if (!userResponse.isSuccessful() || !userResponse.getResponse().isPresent()) {
					messageHandler.sendIn(player, AlertModes.ERROR, "whisper.error");
					return;
				}

				User user = userResponse.getResponse().get();
				UserDoc.Session session = user.getSession();

				if (session.getLastReplyById() == null) {
					messageHandler.sendIn(player, AlertModes.ERROR, "whisper.no-has-player-to-reply");
					return;
				}

				userFindService.find(session.getLastReplyById()).callback(userResponseTarget -> {

					if (!userResponseTarget.isSuccessful() || !userResponseTarget.getResponse().isPresent()) {
						messageHandler.sendIn(player, AlertModes.ERROR, "whisper.error");
						return;
					}

					User userTarget = userResponseTarget.getResponse().get();

					if (!userTarget.getSession().isOnline()) {
						messageHandler.sendIn(player, AlertModes.ERROR, "commands.player.offline");
						return;
					}

					whisperSender.sendWhisper(player, userTarget, user, message);
				});

			});
	}

}

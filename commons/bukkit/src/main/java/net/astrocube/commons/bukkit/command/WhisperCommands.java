package net.astrocube.commons.bukkit.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.Text;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.bukkit.whisper.WhisperManager;
import net.astrocube.commons.bukkit.whisper.WhisperResponse;
import net.astrocube.commons.bukkit.whisper.WhisperSender;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.Optional;
import java.util.logging.Level;

public class WhisperCommands implements CommandClass {

	private @Inject QueryService<User> userQueryService;
	private @Inject FindService<User> userFindService;

	private @Inject WhisperSender whisperSender;

	private @Inject ObjectMapper mapper;
	private @Inject MessageHandler messageHandler;

	@Command(names = {"whisper", "msg", "m", "tell", "t", "w"})
	public boolean whisper(@Sender Player sender, String target, @Text String message) {

		ObjectNode query = mapper.createObjectNode();
		query.put("username", target);

		userFindService.find(sender.getDatabaseIdentifier())
			.callback(userResponse -> {
				if (!userResponse.isSuccessful() || !userResponse.getResponse().isPresent()) {
					messageHandler.sendIn(sender, AlertModes.ERROR, "whisper.error");
					return;
				}

				User user = userResponse.getResponse().get();

				userQueryService.query(query)
					.callback(response -> {
						if (!response.isSuccessful() || !response.getResponse().isPresent()) {
							messageHandler.sendIn(sender, AlertModes.ERROR, "whisper.error");
							return;
						}

						Optional<User> targetOptional = response.getResponse().get().getFoundModels().stream().findFirst();

						if (!targetOptional.isPresent()) {
							messageHandler.sendIn(sender, AlertModes.ERROR, "commands.player.offline");
							return;
						}

						User targetUser = targetOptional.get();

						if (!targetUser.getSession().isOnline()) {
							messageHandler.sendIn(sender, AlertModes.ERROR, "commands.player.offline");
							return;
						}

						whisperSender.sendWhisper(sender, targetUser, user, message);
					});
			});

		return true;

	}


}

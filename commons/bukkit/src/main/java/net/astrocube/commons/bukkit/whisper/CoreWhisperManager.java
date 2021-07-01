package net.astrocube.commons.bukkit.whisper;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.fixeddev.minecraft.player.Player;
import me.yushust.message.MessageHandler;
import net.astrocube.api.core.concurrent.ExecutorServiceProvider;
import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.message.Messenger;
import net.astrocube.api.core.service.update.UpdateService;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.api.core.virtual.user.UserDoc;
import org.bukkit.Bukkit;

import javax.inject.Inject;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class CoreWhisperManager implements WhisperManager {

	private final Channel<WhisperMessage> whisperMessageChannel;
	private final ExecutorServiceProvider executorServiceProvider;
	private final UpdateService<User, UserDoc.Partial> updateService;

	private final MessageHandler messageHandler;

	@Inject
	private CoreWhisperManager(Messenger messenger,
														 ExecutorServiceProvider executorServiceProvider,
														 MessageHandler handler, UpdateService<User, UserDoc.Partial> updateService) {
		this.executorServiceProvider = executorServiceProvider;
		this.messageHandler = handler;
		this.updateService = updateService;
		whisperMessageChannel = messenger.getChannel(WhisperMessage.class);
	}

	@Override
	public CompletableFuture<WhisperResponse> sendWhisper(Player sender, User target, User senderUser, String message) {

		UserDoc.Session session = target.getSession();

		if (!session.isOnline()) {
			return CompletableFuture.completedFuture(new CoreWhisperResponse(WhisperResponse.Result.FAILED_OFFLINE));
		}

		Player targetPlayer = Bukkit.getPlayerByIdentifier(target.getId());

		// is online on other server
		if (targetPlayer == null) {

			senderUser.getSession().setLastReplyById(target.getId());
			updateService.update(senderUser);

			messageHandler
				.sendReplacing(sender, "whisper.sender",
					"%sender%", senderUser.getDisplay(),
					"%target%", target.getDisplay(),
					"%message%", message);

			WhisperMessage whisperMessage = new CoreWhisperMessage(senderUser, target, message);

			return CompletableFuture.supplyAsync(() -> {
				try {
					whisperMessageChannel.sendMessage(whisperMessage, new HashMap<>());
					return new CoreWhisperResponse(WhisperResponse.Result.SUCCESS, whisperMessage);
				} catch (JsonProcessingException e) {
					return new CoreWhisperResponse(WhisperResponse.Result.FAILED_ERROR, Collections.singletonList(e));
				}
			}, executorServiceProvider.getRegisteredService());
		}

		// online on the same server
		messageHandler
			.sendReplacing(targetPlayer, "whisper.target",
				"%sender%", senderUser.getDisplay(),
				"%target%", target.getDisplay(),
				"%message%", message);
		messageHandler
			.sendReplacing(sender, "whisper.sender",
				"%sender%", senderUser.getDisplay(),
				"%target%", target.getDisplay(),
				"%message%", message);


		return CompletableFuture.completedFuture(new CoreWhisperResponse(WhisperResponse.Result.SUCCESS, new CoreWhisperMessage(senderUser, target, message)));
	}

}

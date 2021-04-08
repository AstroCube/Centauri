package net.astrocube.commons.bukkit.whisper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.util.concurrent.ListeningExecutorService;
import me.fixeddev.minecraft.player.Player;
import me.yushust.message.MessageHandler;
import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.message.Messenger;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.api.core.virtual.user.UserDoc;

import javax.inject.Inject;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class CoreWhisperManager implements WhisperManager {

    private final Channel<WhisperMessage> whisperMessageChannel;
    private final ExecutorService executorService;

    private final MessageHandler messageHandler;

    @Inject
    private CoreWhisperManager(Messenger messenger,
                               ListeningExecutorService executorService,
                               FindService<User> userFindService,
                               MessageHandler handler) {
        this.executorService = executorService;
        this.messageHandler = handler;

        whisperMessageChannel = messenger.getChannel(WhisperMessage.class);
    }

    @Override
    public CompletableFuture<WhisperResponse> sendWhisper(Player sender, User target, User senderUser, String message) {
        UserDoc.Session session = target.getSession();

        if (!session.isOnline()) {
            return CompletableFuture.completedFuture(new CoreWhisperResponse(WhisperResponse.Result.FAILED_OFFLINE));
        }

        Player targetPlayer = getPlayerById(target.getId());

        // is online on other server
        if (targetPlayer == null) {
            messageHandler
                    .send(sender, "whisper.sender",
                            senderUser.getDisplay(),
                            target.getDisplay(),
                            message);

            WhisperMessage whisperMessage = new CoreWhisperMessage(senderUser, target, message);

            return CompletableFuture.supplyAsync(() -> {
                try {
                    whisperMessageChannel.sendMessage(whisperMessage, new HashMap<>());

                    return new CoreWhisperResponse(WhisperResponse.Result.SUCCESS, whisperMessage);
                } catch (JsonProcessingException e) {
                    return new CoreWhisperResponse(WhisperResponse.Result.FAILED_ERROR, Collections.singletonList(e));
                }
            }, executorService);
        }

        // online on the same server
        messageHandler
                .send(targetPlayer, "whisper.target",
                        senderUser.getDisplay(),
                        target.getDisplay(),
                        message);
        messageHandler
                .send(sender, "whisper.sender",
                        senderUser.getDisplay(),
                        target.getDisplay(),
                        message);


        return CompletableFuture.completedFuture(new CoreWhisperResponse(WhisperResponse.Result.SUCCESS, new CoreWhisperMessage(senderUser, target, message)));
    }

    private Player getPlayerById(String id) {
        return null;
    }
}

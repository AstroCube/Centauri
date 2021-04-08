package net.astrocube.commons.bukkit.whisper;

import me.fixeddev.minecraft.player.Player;
import net.astrocube.api.core.message.MessageHandler;
import net.astrocube.api.core.message.Metadata;

public class WhisperListener implements MessageHandler<WhisperMessage> {

    private me.yushust.message.MessageHandler messageHandler;

    WhisperListener(me.yushust.message.MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public Class<WhisperMessage> type() {
        return WhisperMessage.class;
    }

    @Override
    public void handleDelivery(WhisperMessage message, Metadata properties) {
        Player target = getPlayerById(message.targetId());

        if(target == null){
            return; // The player is not online there.
        }

        messageHandler
                .sendReplacing(target, "whisper.target",
                        "%%sender%%", message.senderDisplay(),
                        "%%target%%", message.targetDisplay(),
                        "%%message%%", message);
    }

    private Player getPlayerById(String id) {
        return null;
    }

}

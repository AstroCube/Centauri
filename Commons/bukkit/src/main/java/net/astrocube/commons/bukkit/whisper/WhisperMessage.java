package net.astrocube.commons.bukkit.whisper;

import net.astrocube.api.core.message.Message;
import net.astrocube.api.core.virtual.user.UserDoc;

public interface WhisperMessage extends Message {
    String senderId();

    String senderDisplay();

    String targetId();

    String targetDisplay();

    String message();
}

package net.astrocube.commons.bukkit.whisper;

import net.astrocube.api.core.message.Message;
import net.astrocube.api.core.virtual.user.UserDoc;

public interface WhisperMessage extends Message {
    UserDoc.Identity sender();

    UserDoc.Identity target();

    String message();
}

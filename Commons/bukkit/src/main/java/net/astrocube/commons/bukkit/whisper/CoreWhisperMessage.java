package net.astrocube.commons.bukkit.whisper;

import net.astrocube.api.core.virtual.user.User;
import net.astrocube.api.core.virtual.user.UserDoc;

public class CoreWhisperMessage implements WhisperMessage {

    private final String senderId;
    private final String senderDisplay;

    private final String targetId;
    private final String targetDisplay;

    private final String message;

    public CoreWhisperMessage(User sender, User target, String message) {
        senderId = sender.getId();
        senderDisplay = sender.getDisplay();

        targetId = target.getId();
        targetDisplay = target.getDisplay();

        this.message = message;
    }

    @Override
    public String senderId() {
        return senderId;
    }

    @Override
    public String senderDisplay() {
        return senderDisplay;
    }

    @Override
    public String targetId() {
        return targetId;
    }

    @Override
    public String targetDisplay() {
        return targetDisplay;
    }

    @Override
    public String message() {
        return message;
    }
}

package net.astrocube.commons.bukkit.whisper;

import net.astrocube.api.core.virtual.user.UserDoc;

public class CoreWhisperMessage implements WhisperMessage{

    private final UserDoc.Identity sender;
    private final UserDoc.Identity target;

    private final String message;

    public CoreWhisperMessage(UserDoc.Identity sender, UserDoc.Identity target, String message) {
        this.sender = sender;
        this.target = target;
        this.message = message;
    }

    @Override
    public UserDoc.Identity sender() {
        return sender;
    }

    @Override
    public UserDoc.Identity target() {
        return target;
    }

    @Override
    public String message() {
        return message;
    }
}

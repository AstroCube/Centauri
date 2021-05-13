package net.astrocube.commons.bukkit.whisper;

import net.astrocube.api.core.virtual.user.User;

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
	public String getSenderId() {
		return senderId;
	}

	@Override
	public String getSenderDisplay() {
		return senderDisplay;
	}

	@Override
	public String getTargetId() {
		return targetId;
	}

	@Override
	public String getTargetDisplay() {
		return targetDisplay;
	}

	@Override
	public String getMessage() {
		return message;
	}
}

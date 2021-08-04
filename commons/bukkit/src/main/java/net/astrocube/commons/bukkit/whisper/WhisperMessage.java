package net.astrocube.commons.bukkit.whisper;

import net.astrocube.api.core.message.Message;
import net.astrocube.api.core.virtual.user.User;

public class WhisperMessage implements Message {

	private final String senderId;
	private final String senderDisplay;

	private final String targetId;
	private final String targetDisplay;

	private final String message;

	public WhisperMessage(User sender, User target, String message) {
		senderId = sender.getId();
		senderDisplay = sender.getDisplay();

		targetId = target.getId();
		targetDisplay = target.getDisplay();

		this.message = message;
	}

	public String getSenderId() {
		return senderId;
	}

	public String getSenderDisplay() {
		return senderDisplay;
	}

	public String getTargetId() {
		return targetId;
	}

	public String getTargetDisplay() {
		return targetDisplay;
	}

	public String getMessage() {
		return message;
	}

}

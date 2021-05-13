package net.astrocube.commons.bukkit.whisper;

import net.astrocube.api.core.message.Message;

public interface WhisperMessage extends Message {
	String getSenderId();

	String getSenderDisplay();

	String getTargetId();

	String getTargetDisplay();

	String getMessage();
}

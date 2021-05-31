package net.astrocube.commons.bukkit.whisper;

import java.util.Collection;
import java.util.Optional;

public interface WhisperResponse {

	Result result();

	Collection<Exception> errors();

	Optional<WhisperMessage> message();

	enum Result {
		SUCCESS, FAILED_OFFLINE, FAILED_IGNORED, FAILED_NOT_RECEIVING, FAILED_ERROR;
	}
}

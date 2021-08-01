package net.astrocube.commons.bukkit.whisper;

import com.google.common.base.Preconditions;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class WhisperResponse {

	private final Result result;
	private final Collection<Exception> errors;
	private final WhisperMessage message;

	public WhisperResponse(Result result, Collection<Exception> errors, WhisperMessage message) {
		this.result = Preconditions.checkNotNull(result);
		this.errors = Preconditions.checkNotNull(errors);
		this.message = message;
	}

	public WhisperResponse(Result result, WhisperMessage message) {
		this(result, Collections.emptyList(), message);
	}

	public WhisperResponse(Result result, Collection<Exception> errors) {
		this(result, Preconditions.checkNotNull(errors), null);
	}

	public WhisperResponse(Result result) {
		this(result, Collections.emptyList(), null);
	}


	public Result getResult() {
		return result;
	}

	public Collection<Exception> getErrors() {
		return errors;
	}

	public Optional<WhisperMessage> getMessage() {
		return Optional.ofNullable(message);
	}

	enum Result {
		SUCCESS,
		FAILED_OFFLINE,
		FAILED_IGNORED,
		FAILED_NOT_RECEIVING,
		FAILED_ERROR
	}

}

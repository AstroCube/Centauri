package net.astrocube.commons.bukkit.whisper;

import com.google.common.base.Preconditions;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class CoreWhisperResponse implements WhisperResponse {

	private final Result result;
	private final Collection<Exception> errors;
	private final WhisperMessage message;

	protected CoreWhisperResponse(Result result, Collection<Exception> errors, WhisperMessage message) {
		this.result = Preconditions.checkNotNull(result);
		this.errors = Preconditions.checkNotNull(errors);
		this.message = message;
	}

	protected CoreWhisperResponse(Result result, WhisperMessage message) {
		this(result, Collections.emptyList(), message);
	}

	protected CoreWhisperResponse(Result result, Collection<Exception> errors) {
		this(result, Preconditions.checkNotNull(errors), null);
	}

	protected CoreWhisperResponse(Result result) {
		this(result, Collections.emptyList(), null);
	}


	@Override
	public Result result() {
		return result;
	}

	@Override
	public Collection<Exception> errors() {
		return errors;
	}

	@Override
	public Optional<WhisperMessage> message() {
		return Optional.ofNullable(message);
	}
}

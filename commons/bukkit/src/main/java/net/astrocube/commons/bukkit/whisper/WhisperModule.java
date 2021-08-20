package net.astrocube.commons.bukkit.whisper;

import net.astrocube.inject.ProtectedModule;
import net.astrocube.api.core.message.ChannelBinder;

public class WhisperModule extends ProtectedModule implements ChannelBinder {

	@Override
	public void configure() {
		bind(WhisperManager.class).to(CoreWhisperManager.class);
		bindChannel(WhisperMessage.class)
			.registerListener(WhisperListener.class);
	}

}

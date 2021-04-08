package net.astrocube.commons.bukkit.whisper;

import me.fixeddev.inject.ProtectedModule;

public class WhisperModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(WhisperManager.class).to(CoreWhisperManager.class);
    }

}

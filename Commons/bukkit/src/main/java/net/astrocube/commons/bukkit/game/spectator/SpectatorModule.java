package net.astrocube.commons.bukkit.game.spectator;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.game.spectator.GhostEffectControl;
import net.astrocube.api.bukkit.game.spectator.LobbyItemProvider;
import net.astrocube.api.bukkit.game.spectator.SpectatorSessionManager;

public class SpectatorModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(GhostEffectControl.class).to(CoreGhostEffectControl.class);
        bind(LobbyItemProvider.class).to(CoreLobbyItemProvider.class);
        bind(SpectatorSessionManager.class).to(CoreSpectatorSessionManager.class);
    }

}

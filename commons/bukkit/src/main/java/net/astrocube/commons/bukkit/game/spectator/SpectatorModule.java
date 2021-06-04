package net.astrocube.commons.bukkit.game.spectator;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.game.spectator.*;

public class SpectatorModule extends ProtectedModule {

	@Override
	public void configure() {
		bind(SpectateRequestAssigner.class).to(CoreSpectateRequestAssigner.class);
		bind(GhostEffectControl.class).to(CoreGhostEffectControl.class);
		bind(LobbyItemProvider.class).to(CoreLobbyItemProvider.class);
		bind(SpectatorSessionManager.class).to(CoreSpectatorSessionManager.class);
		bind(SpectatorLobbyTeleporter.class).to(CoreSpectatorLobbyTeleporter.class);
	}

}

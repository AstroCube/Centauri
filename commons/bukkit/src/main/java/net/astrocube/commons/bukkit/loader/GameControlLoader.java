package net.astrocube.commons.bukkit.loader;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.spectator.GhostEffectControl;
import net.astrocube.api.core.loader.Loader;

public class GameControlLoader implements Loader {

	@Inject
	private GhostEffectControl ghostEffectControl;

	@Override
	public void load() {
			ghostEffectControl.createTeam();
	}
}

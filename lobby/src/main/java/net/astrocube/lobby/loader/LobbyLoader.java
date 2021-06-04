package net.astrocube.lobby.loader;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.lobby.selector.npc.SelectorRegistry;
import net.astrocube.api.core.loader.Loader;

public class LobbyLoader implements Loader {

	private @Inject EventListenerLoader eventLoader;
	private @Inject WorldLoader worldLoader;
	private @Inject CommandLoader commandLoader;
	private @Inject TaskLoader taskLoader;

	private @Inject SelectorRegistry selectorRegistry;

	@Override
	public void load() {
		eventLoader.load();
		worldLoader.load();
		commandLoader.load();
		taskLoader.load();
		selectorRegistry.generateRegistry();
	}

}

package net.astrocube.lobby.loader;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.lobby.selector.npc.SelectorRegistry;
import net.astrocube.api.core.loader.Loader;

import javax.inject.Named;

public class LobbyLoader implements Loader {

	private @Inject
	@Named("events")
	Loader eventLoader;
	private @Inject
	@Named("world")
	Loader worldLoader;
	private @Inject
	@Named("command")
	Loader commandLoader;

	private @Inject SelectorRegistry selectorRegistry;

	@Override
	public void load() {
		eventLoader.load();
		worldLoader.load();
		commandLoader.load();
		selectorRegistry.generateRegistry();
	}

}

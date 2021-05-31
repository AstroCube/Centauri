package net.astrocube.commons.bukkit.game.map;

import net.astrocube.slime.api.exceptions.UnknownWorldException;
import net.astrocube.slime.api.exceptions.WorldInUseException;
import net.astrocube.slime.api.loaders.SlimeLoader;

import java.io.IOException;
import java.util.List;

public class MapDummyLoader implements SlimeLoader {
	@Override
	public byte[] loadWorld(String worldName, boolean readOnly) throws UnknownWorldException, WorldInUseException, IOException {
		throw new UnsupportedOperationException("Calling dummy loader");
	}

	@Override
	public boolean worldExists(String worldName) throws IOException {
		throw new UnsupportedOperationException("Calling dummy loader");
	}

	@Override
	public List<String> listWorlds() throws IOException {
		throw new UnsupportedOperationException("Calling dummy loader");
	}

	@Override
	public void saveWorld(String worldName, byte[] serializedWorld, boolean lock) throws IOException {
		throw new UnsupportedOperationException("Calling dummy loader");
	}

	@Override
	public void unlockWorld(String worldName) throws UnknownWorldException, IOException {
		throw new UnsupportedOperationException("Calling dummy loader");
	}

	@Override
	public boolean isWorldLocked(String worldName) throws UnknownWorldException, IOException {
		throw new UnsupportedOperationException("Calling dummy loader");
	}

	@Override
	public void deleteWorld(String worldName) throws UnknownWorldException, IOException {
		throw new UnsupportedOperationException("Calling dummy loader");
	}
}

package net.astrocube.api.bukkit.world;

import net.astrocube.slime.api.exceptions.*;
import net.astrocube.slime.api.world.SlimeWorld;

import java.io.File;
import java.io.IOException;

public interface SlimeWorldProvider {

	/**
	 * Import a slime world into ANVIL format from a
	 * certain {@link File}.
	 * @param name     to be assigned to the world after imported.
	 * @param file     folder to be read in ANVIL format.
	 * @param editable if the world can be modified and saved.
	 * @return world with slime properties.
	 * @throws WorldAlreadyExistsException if the data source already contains a world with the same name.
	 * @throws InvalidWorldException       if the provided directory does not contain a valid world.
	 * @throws WorldLoadedException        if the world is loaded on the server.
	 * @throws WorldTooBigException        if the world is too big to be imported into the SRF.
	 * @throws IOException                 if the world could not be read or stored.
	 */
	SlimeWorld importWorld(File file, String name, boolean editable) throws WorldTooBigException, InvalidWorldException, IOException, WorldAlreadyExistsException, WorldLoadedException, NewerFormatException, CorruptedWorldException, WorldInUseException, UnknownWorldException;

	/**
	 * Load a world from a desired folder in slime format.
	 * @param name     of the world to be loaded.
	 * @param editable if world can be saved after load or not.
	 * @return slime world cached with slime properties.
	 * @throws NewerFormatException    when the world is created with a newer format.
	 * @throws CorruptedWorldException when the world is corrupt.
	 * @throws WorldInUseException     when the world is actually in use.
	 * @throws UnknownWorldException   when the world is not known at the loader folder.
	 * @throws IOException             when can not parse world correctly.
	 */
	SlimeWorld loadWorld(String name, boolean editable) throws NewerFormatException, CorruptedWorldException, WorldInUseException, UnknownWorldException, WorldTooBigException, IOException;

}

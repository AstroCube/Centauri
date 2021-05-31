package net.astrocube.api.bukkit.tablist;

import org.bukkit.entity.Player;

public interface TablistGenerator {

	/**
	 * Generates a {@link TablistCompound} to be applied at
	 * a player according to implementation requirements.
	 * @param player to be applied
	 * @return generated compound.
	 */
	TablistCompound generate(Player player);

}

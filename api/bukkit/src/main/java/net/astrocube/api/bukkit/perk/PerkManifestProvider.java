package net.astrocube.api.bukkit.perk;

import com.fasterxml.jackson.databind.node.ObjectNode;
import net.astrocube.api.core.virtual.perk.StorablePerk;

import javax.annotation.Nullable;
import java.util.Set;

public interface PerkManifestProvider {

	/**
	 * Creates a perk manifest as an {@link StorablePerk}.
	 * @param playerId to create
	 * @param gameMode to store
	 */
	<T extends AbstractPerk> void createRegistry(
		String playerId,
		String gameMode,
		@Nullable String subMode,
		String type,
		T perk
	) throws Exception;

	<T extends AbstractPerk> Set<StorablePerk> query(ObjectNode query, Class<T> genericClass) throws Exception;

	<T extends AbstractPerk> T transformPerk(StorablePerk perk, Class<T> genericClass);

	/**
	 * updates a certain manifest according to player stored {@link StorablePerk}.
	 * @param playerId to update
	 * @param manifest to update
	 */
	void update(String playerId, StorablePerk manifest) throws Exception;

}

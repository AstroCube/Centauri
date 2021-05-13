package net.astrocube.api.bukkit.punishment;

import net.astrocube.api.core.virtual.punishment.PunishmentDoc;

import java.util.Set;

public interface PresetPunishmentCache {

	/**
	 * @return set of {@link PresetPunishment}
	 */
	Set<PresetPunishment> getPunishments();

	/**
	 * @param type to be queried
	 * @return set of {@link PresetPunishment}
	 */
	Set<PresetPunishment> getPunishments(PunishmentDoc.Identity.Type type);

	/**
	 * Load punishments to a local cache
	 */
	void generateCache();

}

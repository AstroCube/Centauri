package net.astrocube.api.bukkit.user.profile;

import java.util.Set;
import java.util.UUID;

public interface AbstractGameProfile {

	/**
	 * @return id of the profile
	 */
	UUID getId();

	/**
	 * @return id of the name
	 */
	String getName();

	/**
	 * @return set of mojang properties
	 */
	Set<AbstractProperty> getProperties();

}

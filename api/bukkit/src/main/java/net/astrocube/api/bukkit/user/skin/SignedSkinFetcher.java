package net.astrocube.api.bukkit.user.skin;

import net.astrocube.api.bukkit.user.profile.AbstractProperty;

public interface SignedSkinFetcher {

	/**
	 * Fetch signed skin from minecraft.
	 * @param skin to fetch
	 * @return property of skin
	 */
	AbstractProperty fetch(String skin);

}
